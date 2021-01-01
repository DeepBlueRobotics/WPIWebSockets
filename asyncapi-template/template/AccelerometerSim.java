{%- from "../partials/template.java" import template -%}
{{- template("Accelerometer", "Accel", true, asyncapi.components().schema("accelData")) -}}
