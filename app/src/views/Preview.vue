<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <div>Imagine preview here.</div>
      <div>{{ message }}</div>
      <ion-spinner id="spinner"></ion-spinner>
    </ion-content>

    <ion-footer>
      <ion-grid>
        <ion-row>
          <ion-col size="6">
            <ion-button color="success" expand="block" router-link="/conversation">Send</ion-button>
          </ion-col>
          <ion-col size="6">
            <ion-button color="danger" expand="block" router-link="/conversation">Discard</ion-button>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-footer>
  </ion-page>
</template>

<script>
import {
  IonContent,
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonFooter,
  IonGrid,
  IonRow,
  IonCol,
  IonButton,
  IonSpinner
} from "@ionic/vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "Preview",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonFooter,
    IonGrid,
    IonRow,
    IonCol,
    IonButton,
    IonSpinner
  },
  data() {
    return {
      recording: false
    };
  },
  computed: {
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
  },
});
</script>

<style scoped>
</style>
