{%- from "../partials/template.java" import template -%}
{{- template("PWM", "PWM", true, asyncapi.components().schema("pwmData")) -}}
