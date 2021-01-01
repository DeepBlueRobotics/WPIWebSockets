{%- from "../partials/template.java" import template -%}
{{- template("Relay", "Relay", true, asyncapi.components().schema("relayData")) -}}
