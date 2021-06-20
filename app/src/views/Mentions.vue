<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Mentioned topics</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="closeModal">Close</ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <div v-for="mention in mentions" :key="mention.title">
        <h2>{{ mention.emojis }} - {{ mention.title }}</h2>
        <span v-for="mark in mention.textMarks" :key="mark">
          <a @click="closeModal(mark)">@{{ mark }}</a><br>
        </span>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import {
  IonPage,
  IonContent,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  modalController
} from "@ionic/vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "Mentions",
  props: {
    mentions: Array
  },
  components: {
    IonPage,
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonButtons,
    IonButton
  },
  data() {
    return {
    };
  },
  methods: {
    closeModal(mark) {
      modalController.dismiss({ mark });
    }
  }
});
</script>

<style scoped>
</style>
