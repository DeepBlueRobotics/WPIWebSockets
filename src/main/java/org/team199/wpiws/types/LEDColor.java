package org.team199.wpiws.types;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonKey;

public class LEDColor {

    public final int r, g, b;

    public LEDColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static LEDColor fromJson(JsonObject obj) {
        return new LEDColor((obj.getInteger(Keys.R)), obj.getInteger(Keys.G), obj.getInteger(Keys.B));
    }

    private enum Keys implements JsonKey {
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

}
