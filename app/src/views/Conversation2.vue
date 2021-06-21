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

    <ion-content>
      <ion-card v-for="message in messages" :key="message.id" :style="cardStyle(message)" @click="openMessage(message)">
        <ion-card-header style="padding: 3px">
          <!-- <ion-card-subtitle>sub</ion-card-subtitle> -->
          <ion-card-title style="font-size: 0.8em; vertical-align: middle">{{ message.time }}</ion-card-title>
        </ion-card-header>
        <ion-card-content style="padding: 0">
          <div :style="backgroundGif(message)"></div>
          <div style="padding: 8px; text-align: center; font-size: 1.3em">
            <span v-for="topic in message.mentions" :key="topic.id" style="margin: 0 5px">{{ topic.emojis }}</span>
          </div>
        </ion-card-content>
      </ion-card>

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
  // IonList,
  // IonItem,
  IonIcon,
  IonFab,
  IonFabButton,
  // IonThumbnail,
  IonButtons,
  IonButton,
  IonCard,
  IonCardHeader,
  // IonCardSubtitle,
  IonCardTitle,
  IonCardContent
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { personCircle, personCircleOutline, mic, chatboxOutline, textOutline,
  // enterOutline
} from 'ionicons/icons';
import * as Api from '@/api.js';

addIcons({
  'person-circle': personCircle,
  'person-circle-outline': personCircleOutline,
  mic,
  'chatbox-outline': chatboxOutline,
  'text-outline': textOutline,
  // 'enter-outline': enterOutline
});

export default defineComponent({
  name: "Conversation",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    // IonList,
    // IonItem,
    IonIcon,
    IonFab,
    IonFabButton,
    // IonThumbnail,
    IonButtons,
    IonButton,
    IonCard,
    IonCardHeader,
    // IonCardSubtitle,
    IonCardTitle,
    IonCardContent
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
    cardStyle(message) {
      if (message.from == this.ownName) {
        return 'width: 80%';
      }
      return 'width: 80%; margin-left: auto';
    },
    backgroundGif(message) {
      return `height: 100px; width: 100%; margin: 0; padding: 0; background-image: url(${message.gifUrl}); background-size: contain; background-position: center`;
    },
    messageLabel(message) {
      const from = this.ownName == message.from ? 'You' : message.from;
      return from + ': ' + message.content;
    },
    messageIcon(message) {
      return this.ownName == message.from ? 'person-circle' : 'person-circle-outline';
    },
    loadMessages() {
      Api.getConversation2(this.ownName, this.partnersName)
        .then(response => response.json())
        .then(messages => this.messages = messages);
    },
    openMessage(message) {
      // this.$store.commit('setOpenMessage', message);
      // this.$router.push('message');
      this.$router.push({
        name: 'Message',
        params: { id: message.id }
      });
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
