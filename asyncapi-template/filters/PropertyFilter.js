const objectTypes = [
    { schema: "AddressableLED", propName: "<data", type: "LEDColor", initialValue: "new LEDColor(0, 0, 0)" }
];

function findObjectType(schemaName, propName) {
    return objectTypes.find(obj => obj.schema === schemaName && obj.propName === propName);
}

function formatPropName(name) {
    var sname = name.substring(name.startsWith("<>") ? 2 : 1);
    var parts = sname.split("_");
    //Credit for condtion: https://stackoverflow.com/questions/37896484/multiple-conditions-for-javascript-includes-method
    var mv1toend = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'].some(el => parts[0].includes(el));
    var out = "";
    for(var i = mv1toend ? 1 : 0; i < parts.length; i++) {
        out += cap1(parts[i]);
    }
    if(mv1toend) {
        out += cap1(parts[0]);
    }
    return out;
}

function cap1(str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
}

function formatPropType(prop, schemaName, propName) {
    if(prop.type() === "array") {
        return formatPropType(prop.items(), schemaName, propName) + "[]";
    }
    switch(prop.type()) {
        case "boolean":
            return "boolean";
        case "integer":
            return "int";
        case "number":
            return "double";
        case "string":
            return "String";
        case "object":
            return findObjectType(schemaName, propName)?.type;
        default:
            return undefined;
    }
}

function formatPropObjType(prop, schemaName, propName) {
    switch(prop.type()) {
        case "boolean":
            return "Boolean";
        case "integer":
            return "Integer";
        case "number":
            return "Double";
        default:
            return formatPropType(prop, schemaName, propName);
    }
}

function formatPropCallbackType(prop, schemaName, propName) {
    switch(prop.type()) {
        case "boolean":
            return "BooleanCallback";
        case "integer":
            return "IntegerCallback";
        case "number":
            return "DoubleCallback";
        default:
            return "ObjectCallback<" + formatPropType(prop, schemaName, propName) + ">";
    }
}

function formatPropParser(prop, schemaName, propName) {
    while(prop.type() === "array") prop = prop.items();
    switch(prop.type()) {
        case "boolean":
        case "integer":
        case "number":
        case "string":
            return "null";
        default:
            return findObjectType(schemaName, propName)?.type + "::fromJson";
    }
}

function formatPropInitialValue(prop, schemaName, propName) {
    if(prop.type() === "array") {
        var str = formatPropType(prop, schemaName, propName);
        return "new " + str.substring(0, str.length-1) + "0]";
    }
    switch(prop.type()) {
        case "boolean":
            return "false";
        case "integer":
        case "number":
            return "0";
        case "string":
            return '""';
        case "object":
            return findObjectType(schemaName, propName)?.initialValue;
        default:
            return undefined;
    }
}

function isRobotInput(prop) {
    return prop.startsWith(">") || prop.startsWith("<>");
}

function isRobotOutput(prop) {
    return prop.startsWith("<");
}

function usesCustomTypes(props, schemaName) {
    for (const [propName, prop] of Object.entries(props)) {
        if (formatPropParser(prop, schemaName, propName) !== "null") return true;
    }
    return false;
}

module.exports = {
    formatPropName,
    cap1,
    formatPropType,
    formatPropObjType,
    formatPropCallbackType,
    formatPropParser,
    formatPropInitialValue,
    isRobotInput,
    isRobotOutput,
    usesCustomTypes
}
