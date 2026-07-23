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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Operations to simplify common {@link MessageDigest} tasks. This class is immutable and thread-safe. However the {@link MessageDigest} instances
 * it creates generally won't be.
 * <p>
 * The {@link MessageDigestAlgorithms} class provides constants for standard digest algorithms that can be used with the {@link #getDigest(String)} method and
 * other methods that require the Digest algorithm name.
 * </p>
 * <p>
 * Note: The class has shorthand methods for all the algorithms present as standard in Java 8. This approach requires lots of methods for each algorithm, and
 * quickly becomes unwieldy. The following code works with all algorithms:
 * </p>
 *
 * <pre>
 * import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_224;
 * ...
 * byte [] digest = new DigestUtils(SHA_224).digest(dataToDigest);
 * String hdigest = new DigestUtils(SHA_224).digestAsHex(new File("pom.xml"));
 * </pre>
 * <p>
 * See <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA">Appendix A in the Java Cryptography Architecture
 * Reference Guide</a> for information about standard algorithm names.
 * </p>
 * <p>
 * The use of SHA3-512 requires Java 9 or higher.
 * </p>
 *
 * @see MessageDigestAlgorithms
 */
public class DigestUtils {

    /**
     * Package-private for tests.
     */
    static final int BUFFER_SIZE = 1024;

    public static byte[] digest(final MessageDigest messageDigest, final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] digest(final MessageDigest messageDigest, final ByteBuffer data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] digest(final MessageDigest messageDigest, final File data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] digest(final MessageDigest messageDigest, final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] digest(final MessageDigest messageDigest, final Path data, final OpenOption... options) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] digest(final MessageDigest messageDigest, final RandomAccessFile data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getDigest(final String algorithm) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getDigest(final String algorithm, final MessageDigest defaultMessageDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getMd2Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getMd5Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets a {@code MessageDigest} for the given {@code algorithm}.
     *
     * @param algorithm the name of the algorithm requested. See
     *                  <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA"> Appendix A in the Java
     *                  Cryptography Architecture Reference Guide</a> for information about standard algorithm names.
     * @return A digest instance.
     * @see MessageDigest#getInstance(String)
     * @throws NoSuchAlgorithmException if no Provider supports a MessageDigestSpi implementation for the specified algorithm.
     */
    private static MessageDigest getMessageDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    public static MessageDigest getSha1Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha256Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha3_224Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha3_256Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha3_384Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha3_512Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha384Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha512_224Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha512_256Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getSha512Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets an SHA-1 digest.
     *
     * @return An SHA-1 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @deprecated (1.11) Use {@link #getSha1Digest()}
     */
    @Deprecated
    public static MessageDigest getShaDigest() {
        return getSha1Digest();
    }

    public static MessageDigest getShake128_256Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest getShake256_512Digest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isAvailable(final String messageDigestAlgorithm) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md2(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md2(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md2(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md2Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md2Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md2Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md5(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md5(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] md5(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest.
     * @return SHA-1 digest.
     * @deprecated (1.11) Use {@link #sha1(byte[])}
     */
    @Deprecated
    public static byte[] sha(final byte[] data) {
        return sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest.
     * @return SHA-1 digest.
     * @throws IOException On error reading from the stream.
     * @since 1.4
     * @deprecated (1.11) Use {@link #sha1(InputStream)}
     */
    @Deprecated
    public static byte[] sha(final InputStream data) throws IOException {
        return sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest.
     * @return SHA-1 digest.
     * @deprecated (1.11) Use {@link #sha1(String)}
     */
    @Deprecated
    public static byte[] sha(final String data) {
        return sha1(data);
    }

    public static byte[] sha1(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha1(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha1(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha1Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha1Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha1Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha256(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha256(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha256(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha256Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha256Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha256Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_224(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_224(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_224(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_224Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_224Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_224Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_256(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_256(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_256(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_256Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_256Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_256Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_384(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_384(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_384(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_384Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_384Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_384Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_512(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_512(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha3_512(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_512Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_512Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha3_512Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha384(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha384(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha384(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha384Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha384Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha384Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_224(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_224(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_224(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_224Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_224Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_224Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_256(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_256(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sha512_256(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_256Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_256Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512_256Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String sha512Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hexadecimal string.
     *
     * @param data Data to digest.
     * @return SHA-1 digest as a hexadecimal string.
     * @deprecated (1.11) Use {@link #sha1Hex(byte[])}
     */
    @Deprecated
    public static String shaHex(final byte[] data) {
        return sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hexadecimal string.
     *
     * @param data Data to digest.
     * @return SHA-1 digest as a hexadecimal string.
     * @throws IOException On error reading from the stream.
     * @since 1.4
     * @deprecated (1.11) Use {@link #sha1Hex(InputStream)}
     */
    @Deprecated
    public static String shaHex(final InputStream data) throws IOException {
        return sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hexadecimal string.
     *
     * @param data Data to digest.
     * @return SHA-1 digest as a hexadecimal string.
     * @deprecated (1.11) Use {@link #sha1Hex(String)}
     */
    @Deprecated
    public static String shaHex(final String data) {
        return sha1Hex(data);
    }

    public static byte[] shake128_256(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shake128_256(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shake128_256(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake128_256Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake128_256Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake128_256Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shake256_512(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shake256_512(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shake256_512(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake256_512Hex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake256_512Hex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String shake256_512Hex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest updateDigest(final MessageDigest messageDigest, final byte[] valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest updateDigest(final MessageDigest messageDigest, final ByteBuffer valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest updateDigest(final MessageDigest digest, final File data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reads through a FileChannel and updates the digest for the data using non-blocking-io (NIO).
     *
     * TODO Decide if this should be public.
     *
     * @param digest The MessageDigest to use (for example MD5).
     * @param data   Data to digest.
     * @return the digest.
     * @throws IOException On error reading from the stream.
     * @since 1.14
     */
    private static MessageDigest updateDigest(final MessageDigest digest, final FileChannel data) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (data.read(buffer) > 0) {
            buffer.flip();
            digest.update(buffer);
            buffer.clear();
        }
        return digest;
    }

    public static MessageDigest updateDigest(final MessageDigest digest, final InputStream inputStream) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest updateDigest(final MessageDigest digest, final Path path, final OpenOption... options) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Closing RandomAccessFile closes the channel.
    @SuppressWarnings("resource")
    public static MessageDigest updateDigest(final MessageDigest digest, final RandomAccessFile data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static MessageDigest updateDigest(final MessageDigest messageDigest, final String valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private final MessageDigest messageDigest;

    /**
     * Preserves binary compatibility only. As for previous versions does not provide useful behavior
     *
     * @deprecated Since 1.11; only useful to preserve binary compatibility.
     */
    @Deprecated
    public DigestUtils() {
        this.messageDigest = null;
    }

    /**
     * Creates an instance using the provided {@link MessageDigest} parameter.
     *
     * This can then be used to create digests using methods such as {@link #digest(byte[])} and {@link #digestAsHex(File)}.
     *
     * @param digest the {@link MessageDigest} to use.
     * @since 1.11
     */
    public DigestUtils(final MessageDigest digest) {
        this.messageDigest = digest;
    }

    /**
     * Creates an instance using the provided {@link MessageDigest} parameter.
     *
     * This can then be used to create digests using methods such as {@link #digest(byte[])} and {@link #digestAsHex(File)}.
     *
     * @param name the name of the {@link MessageDigest} to use.
     * @see #getDigest(String)
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @since 1.11
     */
    public DigestUtils(final String name) {
        this(getDigest(name));
    }

    public byte[] digest(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest(final ByteBuffer data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest(final File data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest(final Path data, final OpenOption... options) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] digest(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final ByteBuffer data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final File data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final InputStream data) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final Path data, final OpenOption... options) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String digestAsHex(final String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MessageDigest getMessageDigest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
