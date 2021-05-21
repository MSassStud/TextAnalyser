<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <p>Imagine preview here...</p>
      <p>{{ message }}</p>

      <ion-fab slot="fixed" vertical="bottom" horizontal="center">
        <ion-fab-button @click="send" color="success">
          <ion-icon name="checkmark-outline"></ion-icon>
        </ion-fab-button>
      </ion-fab>
      <ion-fab slot="fixed" vertical="bottom" horizontal="end">
        <ion-fab-button router-link="/conversation" color="danger">
          <ion-icon name="close-outline"></ion-icon>
        </ion-fab-button>
      </ion-fab>
    </ion-content>

  </ion-page>
</template>

<script>
import {
  IonContent,
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonFab,
  IonFabButton
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { checkmarkOutline, closeOutline } from 'ionicons/icons';
addIcons({ 'checkmark-outline': checkmarkOutline, 'close-outline': closeOutline });

export default defineComponent({
  name: "Preview",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonFab,
    IonFabButton
  },
  data() {
    return {
      recording: false
    };
  },
  computed: {
    ownName() {
      return this.$store.state.ownName;
    },
    partnersName() {
      return this.$store.state.partnersName;
    },
    message() {
      return this.$store.state.message;
    }
  },
  methods: {
    record() {
      this.recording = true;
    },
    send() {
      fetch('http://localhost:8080/messages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          from: this.ownName,
          to: this.partnersName,
          message: this.message
        })
      }).then(response => this.$router.push('/conversation'))
      .catch(error => console.error('fetch error', error));
    }
  },
});
</script>

<style scoped>
</style>
