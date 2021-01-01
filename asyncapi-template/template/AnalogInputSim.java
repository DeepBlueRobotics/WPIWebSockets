{%- from "../partials/template.java" import template -%}
{{- template("AnalogInput", "AI", true, asyncapi.components().schema("aiData")) -}}
