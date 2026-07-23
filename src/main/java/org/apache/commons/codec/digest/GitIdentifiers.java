/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.codec.digest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

/**
 * Computes <a href="https://git-scm.com/">Git</a> object identifiers and their generalizations described by the
 * <a href="https://www.swhid.org/swhid-specification/">SWHID specification</a>.
 *
 * <p>When the hash algorithm is SHA-1, the identifiers produced by this class are identical to those used by Git.
 * Other hash algorithms produce generalized identifiers as described by the SWHID specification.</p>
 *
 * <p>This class is immutable and thread-safe. However, the {@link MessageDigest} instances passed to it generally won't be.</p>
 *
 * @see <a href="https://git-scm.com/book/en/v2/Git-Internals-Git-Objects">Git Internals – Git Objects</a>
 * @see <a href="https://www.swhid.org/swhid-specification/">SWHID Specification</a>
 * @since 1.22.0
 */
public class GitIdentifiers {

    /**
     * Represents a single entry in a Git tree object.
     *
     * <p>A Git tree object encodes a directory snapshot. Each entry holds:</p>
     * <ul>
     *   <li>a {@link FileMode} that determines the Unix file mode (e.g. {@code 100644} for a regular file),</li>
     *   <li>the entry name (file or directory name, without a path separator),</li>
     *   <li>the raw object id of the referenced blob or sub-tree.</li>
     * </ul>
     *
     * <p>Entries are ordered by {@link #compareTo} using Git's tree-sort rule: directory names are compared as if they ended with {@code '/'}, so that {@code foo/}
     * sorts after {@code foobar}.</p>
     *
     * @see <a href="https://git-scm.com/book/en/v2/Git-Internals-Git-Objects">Git Internals – Git Objects</a>
     * @see <a href="https://www.swhid.org/swhid-specification/v1.2/5.Core_identifiers/#53-directories">SWHID Directory Identifier</a>
     */
    static class DirectoryEntry implements Comparable<DirectoryEntry> {

        /**
         * The entry name (file or directory name, no path separator).
         */
        private final String name;

        /**
         * The raw object id of the referenced blob or sub-tree.
         */
        private final byte[] rawObjectId;

        /**
         * The key used for ordering entries within a tree object.
         *
         * <p>>Git appends {@code '/'} to directory names before comparing.</p>
         */
        private final String sortKey;

        /**
         * The Git object type, which determines the Unix file-mode prefix.
         */
        private final FileMode type;

        /**
         * Constructs a new entry.
         *
         * @param name The name of the entry, not containing {@code '/'}.
         * @param type The type of the entry, not null.
         * @param rawObjectId The id of the entry, not null.
         */
        DirectoryEntry(final String name, final FileMode type, final byte[] rawObjectId) {
            if (Objects.requireNonNull(name).indexOf('/') >= 0) {
                throw new IllegalArgumentException("Entry name must not contain '/': " + name);
            }
            this.name = name;
            this.type = Objects.requireNonNull(type);
            this.sortKey = type == FileMode.DIRECTORY ? name + "/" : name;
            this.rawObjectId = Objects.requireNonNull(rawObjectId);
        }

        @Override
        public int compareTo(final DirectoryEntry o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean equals(final Object obj) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The type of a Git tree entry, which maps to a Unix file-mode string.
     *
     * <p>Git encodes the file type and permission bits as an ASCII octal string that precedes the entry name in the binary tree format. The values defined here
     * cover the four entry types that Git itself produces.</p>
     *
     * @see <a href="https://git-scm.com/book/en/v2/Git-Internals-Git-Objects">Git Internals – Git Objects</a>
     */
    public enum FileMode {

        /**
         * A subdirectory. Subdirectories can only be specified by SHA or through a tree mark set with {@code --import-marks}.
         *
         * @see <a href="https://git-scm.com/docs/git-fast-import">git-fast-import - Backend for fast Git data importers</a>
         */
        DIRECTORY(new byte[] { '4', '0', '0', '0', '0' }),
        /**
         * A regular, but executable, file.
         */
        EXECUTABLE(new byte[] { '1', '0', '0', '7', '5', '5' }),
        /**
         * A gitlink, SHA-1 of the object refers to a commit in another repository. Git links can only be specified either by SHA or through a commit mark. They
         * are used to implement submodules.
         *
         * @see <a href="https://git-scm.com/docs/gitdatamodel">gitdatamodel - Git&#39;s core data model</a>
         * @see <a href="https://git-scm.com/docs/git-fast-import">git-fast-import - Backend for fast Git data importers</a>
         */
        GIT_LINK(new byte[] { '1', '6', '0', '0', '0', '0' }),
        /**
         * A regular (non-executable) file.
         * <p>
         * The majority of files in most projects use this mode. If in doubt, this is what you want.
         * </p>
         */
        REGULAR(new byte[] { '1', '0', '0', '6', '4', '4' }),
        /**
         * A symbolic link. The content of the file will be the link target.
         */
        SYMBOLIC_LINK(new byte[] { '1', '2', '0', '0', '0', '0' });

        private static FileMode get(final Path path) {
            // Symbolic links first
            if (Files.isSymbolicLink(path)) {
                return SYMBOLIC_LINK;
            }
            if (Files.isDirectory(path)) {
                return DIRECTORY;
            }
            if (Files.isExecutable(path)) {
                return EXECUTABLE;
            }
            return REGULAR;
        }

        /**
         * Serialized {@code mode}: since this is mutable, it must remain private.
         */
        private final byte[] modeBytes;

        FileMode(final byte[] modeBytes) {
            // No need for a defensive copy since the array is private and never exposed,
            this.modeBytes = modeBytes;
        }
    }

    /**
     * Builds a Git tree identifier for a virtual directory structure, such as the contents of
     * an archive.
     */
    public static final class TreeIdBuilder implements Supplier<byte[]> {

        /**
         * Supplies a blob identifier that may throw {@link IOException}.
         */
        @FunctionalInterface
        private interface BlobIdSupplier {

            byte[] get() throws IOException;
        }

        private static String requireNoParentTraversal(final String name) {
            if ("..".equals(name)) {
                throw new IllegalArgumentException("Path component not allowed: " + name);
            }
            return name;
        }

        private final Map<String, TreeIdBuilder> dirEntries = new HashMap<>();

        private final Map<String, DirectoryEntry> fileEntries = new HashMap<>();

        private final MessageDigest messageDigest;

        private TreeIdBuilder(final MessageDigest messageDigest) {
            this.messageDigest = Objects.requireNonNull(messageDigest);
        }

        public TreeIdBuilder addDirectory(final String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void addFile(final FileMode mode, final String name, final BlobIdSupplier blobId) throws IOException {
            final int slash = name.lastIndexOf('/');
            if (slash < 0) {
                fileEntries.put(name, new DirectoryEntry(requireNoParentTraversal(name), mode, blobId.get()));
            } else {
                addDirectory(name.substring(0, slash)).addFile(mode, name.substring(slash + 1), blobId);
            }
        }

        public void addFile(final FileMode mode, final String name, final byte[] data) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void addFile(final FileMode mode, final String name, final long dataSize, final InputStream data) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void addSymbolicLink(final String name, final String target) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public byte[] get() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private TreeIdBuilder populate(final Path directory) throws IOException {
            try (DirectoryStream<Path> files = Files.newDirectoryStream(directory)) {
                for (final Path path : files) {
                    final String name = Objects.toString(path.getFileName());
                    final FileMode mode = FileMode.get(path);
                    if (mode == FileMode.DIRECTORY) {
                        addDirectory(name).populate(path);
                    } else {
                        addFile(mode, name, () -> blobId(messageDigest, path));
                    }
                }
            }
            return this;
        }
    }

    public static byte[] blobId(final MessageDigest messageDigest, final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] blobId(final MessageDigest messageDigest, final long dataSize, final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] blobId(final MessageDigest messageDigest, final Path data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static byte[] getGitBlobPrefix(final long dataSize) {
        return getGitPrefix("blob", dataSize);
    }

    private static byte[] getGitPrefix(final String type, final long dataSize) {
        return (type + " " + dataSize + "\0").getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] getGitTreePrefix(final long dataSize) {
        return getGitPrefix("tree", dataSize);
    }

    public static byte[] treeId(final MessageDigest messageDigest, final Path data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TreeIdBuilder treeIdBuilder(final MessageDigest messageDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private GitIdentifiers() {
        // utility class
    }
}
