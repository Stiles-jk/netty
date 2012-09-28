/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec;

public class DecodeResult {

    public static final DecodeResult SUCCESS = new DecodeResult(false, null);

    public static DecodeResult failure(Throwable cause) {
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        return new DecodeResult(false, cause);
    }

    public static DecodeResult partialFailure(Throwable cause) {
        if (cause == null) {
            throw new NullPointerException("cause");
        }
        return new DecodeResult(true, cause);
    }

    private final boolean partial;
    private final Throwable cause;

    protected DecodeResult(boolean partial, Throwable cause) {
        if (partial && cause == null) {
            throw new IllegalArgumentException("successful result cannot be partial.");
        }

        this.partial = partial;
        this.cause = cause;
    }

    public boolean isSuccess() {
        return cause == null;
    }

    public boolean isPartial() {
        return partial;
    }

    public Throwable cause() {
        return cause;
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "success";
        }

        String cause = cause().toString();
        StringBuilder buf = new StringBuilder(cause.length() + 17);
        if (isPartial()) {
            buf.append("partial_");
        }
        buf.append("failure(");
        buf.append(cause);
        buf.append(')');

        return buf.toString();
    }
}
