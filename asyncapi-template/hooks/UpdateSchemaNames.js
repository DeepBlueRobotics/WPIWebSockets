const fs = require('fs');
const deviceFilter = require('../filters/DeviceFilter');

module.exports = {
    'setFileTemplateName': (generator, originalFilename) => {
        for(const [name, schema] of generator.asyncapi.allSchemas()) {
            if(name === originalFilename["originalFilename"] && schema.property("type") !== null) {
                return deviceFilter.formatName(schema.property("type").const()) + "Sim";
            }
        }
        return originalFilename["originalFilename"];
    }
}
