/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * A {@link Reader} that encapsulates other sources of data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.13
 */
public final class ReaderOf extends Reader {

    /**
     * The source.
     */
    private final Unchecked<Reader> source;

    /**
     * Ctor.
     * @param chars Chars
     */
    public ReaderOf(final char... chars) {
        this(new InputOf(chars));
    }

    /**
     * Ctor.
     * @param chars Chars
     * @param charset Charset
     */
    public ReaderOf(final char[] chars, final Charset charset) {
        this(new InputOf(chars, charset));
    }

    /**
     * Ctor.
     * @param chars Chars
     * @param charset Charset
     */
    public ReaderOf(final char[] chars, final CharSequence charset) {
        this(new InputOf(chars, charset));
    }

    /**
     * Ctor.
     * @param bytes Bytes
     */
    public ReaderOf(final byte[] bytes) {
        this(new InputOf(bytes));
    }

    /**
     * Ctor.
     * @param bytes Bytes
     * @param charset Charset
     */
    public ReaderOf(final byte[] bytes, final Charset charset) {
        this(new InputOf(bytes), charset);
    }

    /**
     * Ctor.
     * @param bytes Bytes
     * @param charset Charset
     */
    public ReaderOf(final byte[] bytes, final CharSequence charset) {
        this(new InputOf(bytes), charset);
    }

    /**
     * Ctor.
     * @param path The path
     */
    public ReaderOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param file The file
     */
    public ReaderOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     * @param url The URL
     */
    public ReaderOf(final URL url) {
        this(new InputOf(url));
    }

    /**
     * Ctor.
     * @param uri The URI
     */
    public ReaderOf(final URI uri) {
        this(new InputOf(uri));
    }

    /**
     * Ctor.
     * @param bytes The text
     */
    public ReaderOf(final Bytes bytes) {
        this(new InputOf(bytes));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public ReaderOf(final Text text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public ReaderOf(final Text text, final Charset charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public ReaderOf(final Text text, final CharSequence charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public ReaderOf(final CharSequence text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public ReaderOf(final CharSequence text, final Charset charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public ReaderOf(final CharSequence text, final CharSequence charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param input The input
     */
    public ReaderOf(final Input input) {
        this(input, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param input The input
     * @param charset The charset
     */
    public ReaderOf(final Input input, final Charset charset) {
        this(() -> new InputStreamReader(input.stream(), charset));
    }

    /**
     * Ctor.
     * @param input The input
     * @param charset The charset
     */
    public ReaderOf(final Input input, final CharSequence charset) {
        this(() -> new InputStreamReader(input.stream(), charset.toString()));
    }

    /**
     * Ctor.
     * @param input The input
     * @param decoder The decoder
     * @since 0.13.1
     */
    public ReaderOf(final Input input, final CharsetDecoder decoder) {
        this(() -> new InputStreamReader(input.stream(), decoder));
    }

    /**
     * Ctor.
     * @param stream The stream
     */
    public ReaderOf(final InputStream stream) {
        this(stream, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param stream The stream
     * @param charset The charset
     */
    public ReaderOf(final InputStream stream, final Charset charset) {
        this(new InputStreamReader(stream, charset));
    }

    /**
     * Ctor.
     * @param stream The stream
     * @param charset The charset
     * @throws UnsupportedEncodingException If fails
     */
    public ReaderOf(final InputStream stream, final CharSequence charset)
        throws UnsupportedEncodingException {
        this(new InputStreamReader(stream, charset.toString()));
    }

    /**
     * Ctor.
     * @param stream The stream
     * @param decoder The charset decoder
     * @since 0.13.1
     */
    public ReaderOf(final InputStream stream, final CharsetDecoder decoder) {
        this(new InputStreamReader(stream, decoder));
    }

    /**
     * Ctor.
     * @param rdr The reader
     */
    private ReaderOf(final Reader rdr) {
        this(() -> rdr);
    }

    /**
     * Ctor.
     * @param src Source
     */
    private ReaderOf(final Scalar<? extends Reader> src) {
        super();
        this.source = new Unchecked<>(
            new Sticky<>(src)
        );
    }

    @Override
    public int read(final char[] cbuf, final int off, final int len)
        throws IOException {
        return this.source.value().read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        this.source.value().close();
    }

}
