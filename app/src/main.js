import { createApp } from 'vue'
import { createStore } from 'vuex';
import App from './App.vue'
import router from './router';

import { IonicVue } from '@ionic/vue';

/* Core CSS required for Ionic components to work properly */
import '@ionic/vue/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/vue/css/normalize.css';
import '@ionic/vue/css/structure.css';
import '@ionic/vue/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/vue/css/padding.css';
import '@ionic/vue/css/float-elements.css';
import '@ionic/vue/css/text-alignment.css';
import '@ionic/vue/css/text-transformation.css';
import '@ionic/vue/css/flex-utils.css';
import '@ionic/vue/css/display.css';

/* Theme variables */
import './theme/variables.css';

const store = createStore({
  state() {
    return {
      ownName: '',
      partnersName: '',
      message: null,
      selectedEmoji: null,
      openMessage: null,
      openMessage2: null
    }
  },
  mutations: {
    setNames(state, names) {
      console.log('setNames');
      console.log(names);
      state.ownName = names.own;
      state.partnersName = names.partner;
    },
    setMessage(state, message) {
      console.log('setMessage');
      console.log(message);
      state.message = message;
    },
    setSelectedEmoji(state, selection) {
      console.log('setSelectedEmoji');
      console.log(selection);
      state.selectedEmoji = selection;
    },
    setOpenMessage(state, message) {
      state.openMessage = message;
    },
    setOpenMessage2(state, message) {
      state.openMessage2 = message;
    }
  }
})

const app = createApp(App)
  .use(IonicVue)
  .use(router)
  .use(store);
  
router.isReady().then(() => {
  app.mount('#app');
});