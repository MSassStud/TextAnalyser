import { isPlatform } from '@ionic/vue';

export function server() {
    return isPlatform('android') ? '10.0.2.2:8080' : 'localhost:8080';
}
