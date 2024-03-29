<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="openTopics">MT</ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div>{{ error }}</div>
      <ion-list>
        <ion-item v-for="message in messages" :key="message.id" @click="openMessage(message)">
          <ion-icon :name="messageIcon(message)" slot="start"></ion-icon>
          <ion-thumbnail slot="start">
          <iframe :src="gifUrl(message)" height="200" :width="200"></iframe>
          </ion-thumbnail>
          <p>{{ message.content }}</p>
        </ion-item>
      </ion-list>

      <ion-fab slot="fixed" vertical="bottom" horizontal="center">
        <ion-fab-button router-link="/record">
          <ion-icon name="mic"></ion-icon>
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
  IonList,
  IonItem,
  IonIcon,
  IonFab,
  IonFabButton,
  IonThumbnail,
  IonButtons,
  IonButton
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { personCircle, personCircleOutline, mic, chatboxOutline, textOutline } from 'ionicons/icons';
import { getServer } from '../config.js';

addIcons({ "person-circle": personCircle, "person-circle-outline": personCircleOutline, mic, 'chatbox-outline': chatboxOutline, 'text-outline': textOutline });

export default defineComponent({
  name: "Conversation",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonList,
    IonItem,
    IonIcon,
    IonFab,
    IonFabButton,
    IonThumbnail,
    IonButtons,
    IonButton
  },
  data() {
    return {
      messages: [],
      error: '',
      polling: null
    };
  },
  computed: {
    ownName() {
      return this.$store.state.ownName;
    },
    partnersName() {
      return this.$store.state.partnersName;
    }
  },
  methods: {
    messageLabel(message) {
      const from = this.ownName == message.from ? 'You' : message.from;
      return from + ': ' + message.content;
    },
    messageIcon(message) {
      return this.ownName == message.from ? 'person-circle' : 'person-circle-outline';
    },
    loadMessages() {
      fetch('http://' + getServer() + '/conversations?a=' + this.ownName + '&b=' + this.partnersName)
        .then(response => response.json())
        .then(data => this.messages = data)
        .catch(error => this.error = error);
    },
    openMessage(message) {
      this.$store.commit('setOpenMessage', message);
      this.$router.push('message');
    },
    openTopics() {
      this.$router.push('topics');
    },
    gifUrl(message) {
      if (message.analysedProperties.gif.data) {
        return message.analysedProperties.gif.data.embedUrl;
      }
      return '';
    }
  },
  ionViewWillEnter() {
    this.loadMessages();

    this.polling = setInterval(this.loadMessages, 3000);
  },
  ionViewWillLeave() {
    clearInterval(this.polling);
  }
});
</script>

<style scoped>

ion-item {
    --min-height: 200px;
}
</style>
