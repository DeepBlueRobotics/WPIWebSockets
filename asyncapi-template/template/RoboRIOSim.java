{%- from "../partials/template.java" import template -%}
{{- template("RoboRIO", "RoboRIO", false, asyncapi.components().schema("roborioData")) -}}
