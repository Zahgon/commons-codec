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
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Simplifies common {@link javax.crypto.Mac} tasks. This class is immutable and thread-safe. However the Mac may not be.
 * <p>
 * <strong>Note: Not all JCE implementations support all algorithms. If not supported, an IllegalArgumentException is thrown.</strong>
 * </p>
 * <p>
 * Sample usage:
 * </p>
 *
 * <pre>
 * import static HmacAlgorithms.*;
 * byte[] key = {1,2,3,4}; // don't use this actual key!
 * String valueToDigest = "The quick brown fox jumps over the lazy dog";
 * byte[] hmac = new HmacUtils(HMAC_SHA_224, key).hmac(valueToDigest);
 * // Mac reuse
 * HmacUtils hm1 = new HmacUtils("HmacAlgoName", key); // use a valid name here!
 * String hexPom = hm1.hmacHex(new File("pom.xml"));
 * String hexNot = hm1.hmacHex(new File("NOTICE.txt"));
 * </pre>
 *
 * @since 1.10
 */
public final class HmacUtils {

    private static final int STREAM_BUFFER_LENGTH = 1024;

    /**
     * Returns an initialized {@code Mac} for the HmacMD5 algorithm.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     *
     * @param key The key for the keyed digest (must not be null).
     * @return A Mac instance initialized with the given key.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @see Mac#getInstance(String)
     * @see Mac#init(Key)
     * @deprecated (1.11) Use {@code getInitializedMac(HmacAlgorithms.HMAC_MD5, byte[])}.
     */
    @Deprecated
    public static Mac getHmacMd5(final byte[] key) {
        return getInitializedMac(HmacAlgorithms.HMAC_MD5, key);
    }

    /**
     * Returns an initialized {@code Mac} for the HmacSHA1 algorithm.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     *
     * @param key The key for the keyed digest (must not be null).
     * @return A Mac instance initialized with the given key.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @see Mac#getInstance(String)
     * @see Mac#init(Key)
     * @deprecated (1.11) Use {@code getInitializedMac(HmacAlgorithms.HMAC_SHA_1, byte[])}.
     */
    @Deprecated
    public static Mac getHmacSha1(final byte[] key) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_1, key);
    }

    /**
     * Returns an initialized {@code Mac} for the HmacSHA256 algorithm.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     *
     * @param key The key for the keyed digest (must not be null).
     * @return A Mac instance initialized with the given key.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @see Mac#getInstance(String)
     * @see Mac#init(Key)
     * @deprecated (1.11) Use {@code getInitializedMac(HmacAlgorithms.HMAC_SHA_256, byte[])}.
     */
    @Deprecated
    public static Mac getHmacSha256(final byte[] key) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_256, key);
    }

    /**
     * Returns an initialized {@code Mac} for the HmacSHA384 algorithm.
     * <p>
     * Every implementation of the Java platform is <em>not</em> required to support this Mac algorithm.
     * </p>
     *
     * @param key The key for the keyed digest (must not be null).
     * @return A Mac instance initialized with the given key.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @see Mac#getInstance(String)
     * @see Mac#init(Key)
     * @deprecated (1.11) Use {@code getInitializedMac(HmacAlgorithms.HMAC_SHA_384, byte[])}.
     */
    @Deprecated
    public static Mac getHmacSha384(final byte[] key) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_384, key);
    }

    /**
     * Returns an initialized {@code Mac} for the HmacSHA512 algorithm.
     * <p>
     * Every implementation of the Java platform is <em>not</em> required to support this Mac algorithm.
     * </p>
     *
     * @param key The key for the keyed digest (must not be null).
     * @return A Mac instance initialized with the given key.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @see Mac#getInstance(String)
     * @see Mac#init(Key)
     * @deprecated (1.11) Use {@code getInitializedMac(HmacAlgorithms.HMAC_SHA_512, byte[])}.
     */
    @Deprecated
    public static Mac getHmacSha512(final byte[] key) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_512, key);
    }

    public static Mac getInitializedMac(final HmacAlgorithms algorithm, final byte[] key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Mac getInitializedMac(final String algorithm, final byte[] key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacMD5 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, byte[]).hmac(byte[])}.
     */
    @Deprecated
    public static byte[] hmacMd5(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacMD5 MAC for the given key and value.
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, byte[]).hmac(InputStream)}.
     */
    @Deprecated
    public static byte[] hmacMd5(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacMD5 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, String).hmac(String)}.
     */
    @Deprecated
    public static byte[] hmacMd5(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) as a hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacMD5 MAC for the given key and value as a hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, byte[]).hmacHex(byte[])}.
     */
    @Deprecated
    public static String hmacMd5Hex(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) as a hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacMD5 MAC for the given key and value as a hexadecimal string (lowercase).
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, byte[]).hmacHex(InputStream)}.
     */
    @Deprecated
    public static String hmacMd5Hex(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacMD5 Message Authentication Code (MAC) as a hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacMD5 MAC for the given key and value as a hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_MD5, String).hmacHex(String)}.
     */
    @Deprecated
    public static String hmacMd5Hex(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA1 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byte[]).hmac(byte[])}.
     */
    @Deprecated
    public static byte[] hmacSha1(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA1 MAC for the given key and value.
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byte[]).hmac(InputStream)}.
     */
    @Deprecated
    public static byte[] hmacSha1(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA1 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, String).hmac(String)}.
     */
    @Deprecated
    public static byte[] hmacSha1(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA1 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byte[]).hmacHex(byte[])}
     */
    @Deprecated
    public static String hmacSha1Hex(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA1 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byte[]).hmacHex(InputStream)}.
     */
    @Deprecated
    public static String hmacSha1Hex(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA1 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA1 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_1, String).hmacHex(String)}.
     */
    @Deprecated
    public static String hmacSha1Hex(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA256 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byte[]).hmac(byte[])}.
     */
    @Deprecated
    public static byte[] hmacSha256(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA256 MAC for the given key and value.
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byte[]).hmac(InputStream)}.
     */
    @Deprecated
    public static byte[] hmacSha256(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA256 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, String).hmac(String)}.
     */
    @Deprecated
    public static byte[] hmacSha256(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA256 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byte[]).hmacHex(byte[])}.
     */
    @Deprecated
    public static String hmacSha256Hex(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA256 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byte[]).hmacHex(InputStream)}.
     */
    @Deprecated
    public static String hmacSha256Hex(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA256 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA256 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_256, String).hmacHex(String)}.
     */
    @Deprecated
    public static String hmacSha256Hex(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA384 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byte[]).hmac(byte[])}.
     */
    @Deprecated
    public static byte[] hmacSha384(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA384 MAC for the given key and value.
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byte[]).hmac(InputStream)}.
     */
    @Deprecated
    public static byte[] hmacSha384(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA384 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, String).hmac(String)}.
     */
    @Deprecated
    public static byte[] hmacSha384(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmac(valueToDigest);
    }

    // hmacSha384
    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA384 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byte[]).hmacHex(byte[])}.
     */
    @Deprecated
    public static String hmacSha384Hex(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA384 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byte[]).hmacHex(InputStream)}.
     */
    @Deprecated
    public static String hmacSha384Hex(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA384 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA384 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_384, String).hmacHex(String)}.
     */
    @Deprecated
    public static String hmacSha384Hex(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA512 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byte[]).hmac(byte[])}.
     */
    @Deprecated
    public static byte[] hmacSha512(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA512 MAC for the given key and value.
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byte[]).hmac(InputStream)}.
     */
    @Deprecated
    public static byte[] hmacSha512(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmac(valueToDigest);
    }

    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA512 MAC for the given key and value.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, String).hmac(String)}.
     */
    @Deprecated
    public static byte[] hmacSha512(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmac(valueToDigest);
    }

    // hmacSha512
    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA512 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byte[]).hmacHex(byte[])}.
     */
    @Deprecated
    public static String hmacSha512Hex(final byte[] key, final byte[] valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest.
     *                      <p>
     *                      The InputStream must not be null and will not be closed.
     *                      </p>
     * @return HmacSHA512 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IOException              If an I/O error occurs.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byte[]).hmacHex(InputStream)}.
     */
    @Deprecated
    public static String hmacSha512Hex(final byte[] key, final InputStream valueToDigest) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmacHex(valueToDigest);
    }

    /**
     * Returns a HmacSHA512 Message Authentication Code (MAC) as hexadecimal string (lowercase) for the given key and value.
     *
     * @param key           The key for the keyed digest (must not be null).
     * @param valueToDigest The value (data) which should to digest (maybe empty or null).
     * @return HmacSHA512 MAC for the given key and value as hexadecimal string (lowercase).
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @deprecated (1.11) Use {@code new HmacUtils(HmacAlgorithms.HMAC_SHA_512, String).hmacHex(String)}.
     */
    @Deprecated
    public static String hmacSha512Hex(final String key, final String valueToDigest) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key).hmacHex(valueToDigest);
    }

    public static boolean isAvailable(final HmacAlgorithms hmacAlgorithms) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isAvailable(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Mac updateHmac(final Mac mac, final byte[] valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Mac updateHmac(final Mac mac, final InputStream valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Mac updateHmac(final Mac mac, final String valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private final Mac mac;

    /**
     * Preserves binary compatibility only. As for previous versions does not provide useful behavior.
     *
     * @deprecated Since 1.11; only useful to preserve binary compatibility.
     */
    @Deprecated
    public HmacUtils() {
        this(null);
    }

    /**
     * Creates an instance using the provided algorithm type.
     *
     * @param algorithm to use.
     * @param key       the key to use.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @since 1.11
     */
    public HmacUtils(final HmacAlgorithms algorithm, final byte[] key) {
        this(algorithm.getName(), key);
    }

    /**
     * Creates an instance using the provided algorithm type.
     *
     * @param algorithm to use.
     * @param key       the key to use.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @since 1.11
     */
    public HmacUtils(final HmacAlgorithms algorithm, final String key) {
        this(algorithm.getName(), StringUtils.getBytesUtf8(key));
    }

    private HmacUtils(final Mac mac) {
        this.mac = mac;
    }

    /**
     * Creates an instance using the provided algorithm type.
     *
     * @param algorithm to use.
     * @param key       the key to use.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @since 1.11
     */
    public HmacUtils(final String algorithm, final byte[] key) {
        this(getInitializedMac(algorithm, key));
    }

    /**
     * Creates an instance using the provided algorithm type.
     *
     * @param algorithm to use.
     * @param key       the key to use.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught or key is null or key is invalid.
     * @since 1.11
     */
    public HmacUtils(final String algorithm, final String key) {
        this(algorithm, StringUtils.getBytesUtf8(key));
    }

    public byte[] hmac(final byte[] valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] hmac(final ByteBuffer valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] hmac(final File valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] hmac(final InputStream valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] hmac(final Path valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] hmac(final String valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final byte[] valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final ByteBuffer valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final File valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final InputStream valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final Path valueToDigest) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String hmacHex(final String valueToDigest) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
