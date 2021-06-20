<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button router-link="/conversation">
            <ion-icon name="arrow-back-outline"></ion-icon>
          </ion-button>
        </ion-buttons>
        <ion-title>{{ partnersName }}</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="openMentionsModal">
            <ion-icon name="star-outline"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <ion-grid style="height: 100%">
        <ion-row style="height: 80%" class="ion-align-items-center ion-align-items-stretch">
          <ion-col style="text-align: center; line-height: 0; font-size: 10em" class="ion-align-self-center">
              <span v-if="emoji != null">{{ emoji.emoji }}</span>
          </ion-col>
        </ion-row>
        <ion-row>
          <ion-col>
            <ion-textarea id="messageText" rows="3" readonly :value="text"></ion-textarea>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>

    <ion-footer>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button v-if="!playing" :disabled="playing" @click="play">
            <ion-icon name="play-outline"></ion-icon>
          </ion-button>
          <ion-button v-if="playing" :disabled="!playing" @click="pause">
            <ion-icon name="pause-outline"></ion-icon>
          </ion-button>
          <ion-button @click="stop">
            <ion-icon name="stop-outline"></ion-icon>
          </ion-button>
        </ion-buttons>
        <ion-range min="0" max="1000" v-model="position" @ionChange="posUpdated"></ion-range>
      </ion-toolbar>
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
  IonRange,
  IonIcon,
  IonButtons,
  IonButton,
  IonFooter,
  IonGrid,
  IonRow,
  IonCol,
  IonTextarea,
  modalController
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import {
  checkmarkOutline,
  closeOutline,
  scanOutline,
  playOutline,
  pauseOutline,
  stopOutline,
  arrowBackOutline,
  starOutline
} from 'ionicons/icons';
import * as Api from '@/api.js';
import Mentions from '@/views/Mentions.vue';

addIcons({
  'checkmark-outline': checkmarkOutline,
  'close-outline': closeOutline,
  'scan-outline': scanOutline,
  'play-outline': playOutline,
  'pause-outline': pauseOutline,
  'stop-outline': stopOutline,
  'arrow-back-outline': arrowBackOutline,
  'star-outline': starOutline
});

const charsPerSecond = 10;

export default defineComponent({
  name: "Message",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonRange,
    IonIcon,
    IonButtons,
    IonButton,
    IonFooter,
    IonGrid,
    IonRow,
    IonCol,
    IonTextarea
  },
  data() {
    return {
      emojis: [],
      position: 0,
      emoji: null,
      selection: false,
      nextId: 1,
      messageDuration: 0,
      playInterval: null,
      playing: false,
      text: ''
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
      if (this.$store.state.openMessage) {
        return this.$store.state.openMessage.content;
      }
      return null;
    },
    message2() {
      return this.$store.state.openMessage2 || {};
    }
  },
  methods: {
    play() {
      this.playing = true;
      this.playInterval = setInterval(this.doPlay, 300);
    },
    doPlay() {
      const progress = this.position + (0.3 / this.messageDuration * 1000.0);
      if (progress >= 1000) {
        this.stop();
      } else {
        this.position = progress;
      }
    },
    pause() {
      clearInterval(this.playInterval);
      this.playing = false;
    },
    stop() {
      clearInterval(this.playInterval);
      this.position = 0.0;
      this.playing = false;
    },
    posUpdated() {
      this.posToEmoji();

      if (this.message) {
        const i = Math.ceil(this.position / 1000.0 * this.message.length);
        this.text = this.message.substring(0, i);
      }

      const textArea = document.getElementById('messageText');
      textArea.scrollTop = textArea.scrollHeight;
    },
    posToEmoji() {
      const normalizedPos = this.position / 1000.0;
      console.log(this.emojis);

      const displayTime = 3.0 / this.messageDuration;

      const emoji = this.emojis
        .filter(item => item.time <= normalizedPos && item.time + displayTime >= normalizedPos)
        .sort((a, b) => a.time - b.time)
        .pop();

      console.log(emoji);

      if (emoji != undefined) {
        this.emoji = emoji;
      } else {
        this.emoji = null;
      }
    },
    storeMessage(message) {
    this.$store.commit('setOpenMessage2', message);
    },
    clearMessage() {
      this.$store.commit('setOpenMessage', null);
      this.$store.commit('setOpenMessage2', null);
    },
    updateMessage(message) {
      let nextId = 1;
      this.emojis = message.emojis.map(item => ({
        id: nextId++,
        emoji: item.emoji,
        time: item.time
      }));

      this.messageDuration = message.text.length / charsPerSecond;

      this.posToEmoji();
    },
    async openMentionsModal() {
      const modal = await modalController.create({
        component: Mentions,
        componentProps: {
          mentions: this.message2.mentions
        }
      });

      modal.onDidDismiss()
        .then(returned => {
          console.log('mark: ' + returned.data.mark);
          if (returned.data.mark != null) {
            this.position = returned.data.mark / this.message2.text.length * 1000;
            this.posUpdated();
          }
        });

      return modal.present();
    }
  },
  ionViewDidEnter() {
    const id = this.$store.state.openMessage.id;

    console.log(this.$store.state.ownName);

    Api.getMessage(id, this.ownName)
      .then(response => response.json())
      .then(message => {
        this.storeMessage(message);
        return message;
      })
      .then(this.updateMessage);
  },
  ionViewDidLeave() {
    this.clearMessage();
  }
});
</script>

<style scoped>
</style>
