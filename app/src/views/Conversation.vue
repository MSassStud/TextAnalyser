<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <div>{{ error }}</div>
      <ion-list>
        <ion-item v-for="message in messages" :key="message.id">
          <ion-icon :name="messageIcon(message)" slot="start"></ion-icon>
          <ion-thumbnail slot="start">
          <iframe :src="message.analysedProperties.gifUrl" :height="60" :width="60"></iframe>
          </ion-thumbnail>
          <ion-label>{{ message.content }}</ion-label>
        </ion-item>
      </ion-list>

      <ion-fab slot="fixed" vertical="bottom" horizontal="center">
        <ion-fab-button>
          <ion-icon name="mic"></ion-icon>
        </ion-fab-button>
        <ion-fab-list side="top">
          <ion-fab-button router-link="/record">
            <ion-icon name="text-outline"></ion-icon>
          </ion-fab-button>
          <ion-fab-button router-link="/recordSpeech">
            <ion-icon name="chatbox-outline"></ion-icon>
          </ion-fab-button>
        </ion-fab-list>
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
  IonLabel,
  IonIcon,
  IonFab,
  IonFabButton,
  IonFabList,
  IonThumbnail
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { personCircle, personCircleOutline, mic, chatboxOutline, textOutline } from 'ionicons/icons';
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
    IonLabel,
    IonIcon,
    IonFab,
    IonFabButton,
    IonFabList,
    IonThumbnail
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
      fetch('http://localhost:8080/conversations?a=' + this.ownName + '&b=' + this.partnersName)
      .then(response => response.json())
      .then(data => this.messages = data)
      .catch(error => this.error = error);
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
</style>
