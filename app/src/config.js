import { isPlatform } from '@ionic/vue';

var server = init();

function getServer() {
    return server;
}

function setServer(host) {
    server = host;
}

function init() {
    return isPlatform('android') ? '10.0.2.2:8080' : 'localhost:8080';
}

export {
    setServer,
    getServer
}
