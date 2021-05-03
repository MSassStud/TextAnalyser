<template>
  <ion-app @recorded-message="onRecordedMessage">
    <ion-router-outlet />
  </ion-app>
</template>

<script>
import { IonApp, IonRouterOutlet } from '@ionic/vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'App',
  components: {
    IonApp,
    IonRouterOutlet
  },
  data() {
    return {
      message: null
    }
  },
  methods: {
    onRecordedMessage(message) {
      this.message = message;
      console.log('new message ' + message);
    }
  }
});
</script>