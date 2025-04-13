package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ByteSize implements Token {
  private final String value;
  private final long bytes;

  public ByteSize(String value) {
    this.value = value;
    this.bytes = parseBytes(value);
  }

  private long parseBytes(String value) {
    value = value.trim().toUpperCase();
    double number = Double.parseDouble(value.replaceAll("[^0-9.]", ""));
    if (value.endsWith("KB")) return (long)(number * 1024);
    if (value.endsWith("MB")) return (long)(number * 1024 * 1024);
    if (value.endsWith("GB")) return (long)(number * 1024 * 1024 * 1024);
    if (value.endsWith("TB")) return (long)(number * 1024L * 1024 * 1024 * 1024);
    return (long) number; // B
  }

  public long getBytes() {
    return bytes;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public TokenType type() {
    return TokenType.BYTESIZE;
  }

  @Override
  public JsonElement toJson() {
    return new JsonPrimitive(value);
  }
}
