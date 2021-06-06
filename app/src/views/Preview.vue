<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding" @selectedEmoji="selectedEmoji">
      <ion-loading :is-open="loading" message="Loading preview..."></ion-loading>
      <!-- <ion-modal :is-open="selection" @didDismiss="closeEmojiSelection">
        <emoji-selection></emoji-selection>
      </ion-modal> -->
      <div style="text-align: center; font-size: 2.5em; height: 3em">
        <span v-if="emoji != null" @click="openModal(emoji)">{{ emoji.emoji }}</span>
        <span v-if="emoji == null" @click="openModal(null)"><ion-icon name="scan-outline"></ion-icon></span>
      </div>
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
  IonFabButton,
  IonLoading,
  IonRange,
  IonIcon,
  IonButtons,
  // IonModal,
  modalController,
  // IonButtons,
  IonButton,
  // IonItem
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { checkmarkOutline, closeOutline, scanOutline, playOutline, pauseOutline, stopOutline } from 'ionicons/icons';
import EmojiSelection from "./EmojiSelection.vue";
addIcons({
  'checkmark-outline': checkmarkOutline,
  'close-outline': closeOutline,
  'scan-outline': scanOutline,
  'play-outline': playOutline,
  'pause-outline': pauseOutline,
  'stop-outline': stopOutline });

const charsPerSecond = 10;

export default defineComponent({
  name: "Preview",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonFab,
    IonFabButton,
    IonLoading,
    IonRange,
    IonIcon,
    IonButtons,
    // IonModal,
    // IonButtons,
    IonButton,
    // EmojiSelection
    // IonItem
  },
  data() {
    return {
      // recording: false,
      emojis: [
          // {emoji: '🙂', time: 0.1},
          // {emoji: '😕', time: 0.2},
          // {emoji: '🙁', time: 0.5},
          // {emoji: '☹️', time: 0.7},
        ],
      loading: true,
      position: 0,
      emoji: null,
      emojiRef: ['☺️', '😊', '😇', '🙂', '🙃', '😔', '😟', '😕', '🙁', '☹️'],
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
      this.playInterval = setInterval(this.doPlay, 500);
    },
    doPlay() {
      const progress = this.position + 100;
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
    async openModal(emoji) {
      if (this.playing) {
        this.pause();
      }
      const controller = modalController;
      const modal = await controller.create({
        component: EmojiSelection,
        componentProps: {
          emoji: emoji
        }
      });
      modal.onDidDismiss().then(returned => {
        console.log('dismissed with ' + returned.data);
        const data = returned.data;
        if (data.prev && data.action.name == 'select') {
          data.prev.emoji = data.action.selection;
        }
        else if (data.prev == null && data.action.name == 'select') {
          this.emojis.push({
            id: this.nextId++,
            emoji: data.action.selection,
            time: this.position / 1000.0
          });
          this.posToEmoji();
        }
        else if (data.action.name == 'delete') {
          this.emojis = this.emojis.filter(item => item.id != data.prev.id);
        }
      });
      return modal.present();
    },
    // record() {
    //   this.recording = true;
    // },
    send() {
      fetch('http://localhost:8080/messages', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          from: this.ownName,
          to: this.partnersName,
          message: this.message,
          data: {
            emojis: this.emojis
              .sort((a, b) => a - b)
              .map(item => ({ emoji: item.emoji, time: item.time }))
          }
        })
      })
      .then(() => this.$router.push('/conversation'))
      .catch(error => console.error('fetch error', error));
    },
    posToEmoji() {
      const normalizedPos = this.position / 1000.0;
      console.log(this.emojis);
      const emoji = this.emojis
        // .map(item => ({
        //   emoji: item.emoji,
        //   distance: Math.abs(normalizedPos - item.time)
        // }))
        // .filter(emoji => emoji.distance < 0.1)
        // .sort((a, b) => b.distance - a.distance)
        .filter(item => item.time <= normalizedPos && item.time + 0.1 >= normalizedPos)
        .pop();

      console.log(emoji);

      if (emoji != undefined) {
        this.emoji = emoji;
      } else {
        this.emoji = null;
      }
    },
    openEmojiSelection(emoji) {
      this.$store.commit('setSelectedEmoji', emoji);

      this.selection = true;
    },
    closeEmojiSelection() {
      console.log('close selction');
      this.selection = false;
    },
    selectedEmoji() {
      console.log('selected emoji');
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
    // this.loading = false;

    fetch('http://localhost:8080/preview', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        message: this.$store.state.message
      })
    })
    .then(response => response.json())
    .then(data => this.emojis = this.emojiTimings(data))
    .finally(() => this.loading = false);
  }
});
</script>

<style scoped>
</style>
