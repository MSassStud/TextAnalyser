<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-button router-link="/record">Record message</ion-button>
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
  IonButton,
} from "@ionic/vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "Conversation",
  components: {
    IonContent,
    IonPage,
    IonButton,
    IonHeader,
    IonToolbar,
    IonTitle,
  },
  data() {
    return {
    };
  },
  computed: {
    partnersName() {
      return this.$store.state.partnersName;
    }
  }
});
</script>

<style scoped>
</style>
