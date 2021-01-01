{% set pfname = propName | safe %}
{% set pname = pfname | formatPropName %}
{% set pnamel = pname | lower %}
{% set pnameu = pname | upper %}
{% set ptype = prop | formatPropType %}
{% set parrtype = prop | formatPropTypeArr %}
{% set pprimtype = prop | formatPropPrimType %}
{% set pinit = prop | formatPropInitialValue | safe %}
