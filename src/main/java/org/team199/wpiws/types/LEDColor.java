package org.team199.wpiws.types;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.Writer;

import com.github.cliftonlabs.json_simple.JsonKey;

public class LEDColor implements Jsonable {

    public final int r, g, b;

    public LEDColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static LEDColor fromJson(JsonObject obj) {
        return new LEDColor((obj.getInteger(Keys.R)), obj.getInteger(Keys.G), obj.getInteger(Keys.B));
    }

    @Override
    public String toJson() {
        return String.format("{\"r\":%d, \"g\": %d, \"b\": %d}", r, g, b);
    }

    @Override
    public void toJson(Writer writable) throws IOException {
        writable.append(toJson());
    }

    private static enum Keys implements JsonKey {
        R,
        G,
        B;

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            return new Exception("Message is missing required key: " + getKey());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LEDColor) {
            LEDColor other = (LEDColor) obj;
            return r == other.r && g == other.g && b == other.b;
        }
        return false;
    }

    @Override
    public String toString() {
        return "LEDColor[r=%d,g=%d,b=%d]".formatted(r, g, b);
    }

}
