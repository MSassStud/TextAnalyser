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
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <!-- <ion-loading :is-open="loading" message="Loading preview..."></ion-loading> -->
      <!-- <ion-modal :is-open="selection" @didDismiss="closeEmojiSelection">
        <emoji-selection></emoji-selection>
      </ion-modal> -->
      <ion-grid style="height: 100%">
        <ion-row style="height: 100%" class="ion-align-items-center ion-align-items-stretch">
          <ion-col style="text-align: center; line-height: 0; font-size: 10em" class="ion-align-self-center">
            <!-- <div style="text-align: center; font-size: 10em"> -->
              <span v-if="emoji != null">{{ emoji.emoji }}</span>
              <!-- <span v-if="emoji == null" @click="openModal(null)"><ion-icon name="scan-outline"></ion-icon></span> -->
            <!-- </div> -->
          </ion-col>
        </ion-row>
      </ion-grid>

      <!-- <ion-fab slot="fixed" vertical="bottom" horizontal="center">
        <ion-fab-button @click="send" color="success">
          <ion-icon name="checkmark-outline"></ion-icon>
        </ion-fab-button>
      </ion-fab>
      <ion-fab slot="fixed" vertical="bottom" horizontal="end">
        <ion-fab-button router-link="/conversation" color="danger">
          <ion-icon name="close-outline"></ion-icon>
        </ion-fab-button>
      </ion-fab> -->
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
        <ion-range min="0" max="1000" v-model="position" @ionChange="posToEmoji"></ion-range>
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
  IonCol
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { checkmarkOutline, closeOutline, scanOutline, playOutline, pauseOutline, stopOutline, arrowBackOutline } from 'ionicons/icons';
addIcons({
  'checkmark-outline': checkmarkOutline,
  'close-outline': closeOutline,
  'scan-outline': scanOutline,
  'play-outline': playOutline,
  'pause-outline': pauseOutline,
  'stop-outline': stopOutline,
  'arrow-back-outline': arrowBackOutline });

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
    IonCol
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
      playing: false
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
    emojiTimings(result) {
      const messageLength = result.message.length;
      const emojis = result.emojis;
      const timing = emojis.map(item => ({
          id: this.nextId++,
          emoji: item.emoji,
          time: item.textStart / messageLength
        }));
      this.messageDuration = messageLength / charsPerSecond;
      console.log(timing);
      return timing;
    }
  },
  ionViewDidEnter() {
    const message = this.$store.state.openMessage;
    console.log(message);

    let nextId = 1;
    this.emojis = message.data.emojis.map(item => ({
      id: nextId++,
      emoji: item.emoji,
      time: item.time
    }));

    this.messageDuration = message.content.length / charsPerSecond;

    this.posToEmoji();
  },
  ionViewDidLeave() {
    this.store.commit('setOpenMessage', null);
  }
});
</script>

<style scoped>
</style>
