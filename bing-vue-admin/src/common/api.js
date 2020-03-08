import commonSystemApi from './module/common-system/common-system-api';
import socialSystemApi from './module/social-system/social-system-api';
import vehicleSystemApi from './module/vehicle-system/vehicle-system-api';
import psbSystemApi from './module/psb-system/psb-system-api';

let API = function(config) {
    let api = {
        "commonSystem": commonSystemApi(config),
        "socialSystem": socialSystemApi(config),
        "vehicleSystem": vehicleSystemApi(config),
        "psbSystem": psbSystemApi(config)
    };
    return api;
}


export default {API}
