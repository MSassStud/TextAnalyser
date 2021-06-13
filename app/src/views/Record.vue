<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <ion-grid>
        <ion-row class="ion-justify-content-center">
          <ion-col style="text-align: center">
            <ion-chip color="danger">
              <ion-icon name="mic-outline" style="font-size: 2em"></ion-icon>
              <ion-spinner name="dots"></ion-spinner>
            </ion-chip>
          </ion-col>
        </ion-row>
        <ion-row>
          <ion-col>
            <ion-item>
              <ion-label position="stacked" class="upper">Your message</ion-label>
              <ion-textarea v-model="message" auto-grow="true"></ion-textarea>
              <ion-button slot="end" :disabled="!speechAvailable" @click="recordSentence">Record speech</ion-button>
            </ion-item>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-fab slot="fixed" vertical="bottom" horizontal="center">
          <ion-fab-button @click="previewTextRecording">
            <ion-icon name="stop"></ion-icon>
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
  IonTextarea,
  IonIcon,
  IonFab,
  IonFabButton,
  IonChip,
  IonGrid,
  IonRow,
  IonCol,
  IonSpinner,
  IonItem,
  IonLabel,
  IonButton
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { micOutline, stop } from 'ionicons/icons';
import { SpeechRecognition } from '@ionic-native/speech-recognition';

addIcons({ 'mic-outline': micOutline, stop });

export default defineComponent({
  name: "Record",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonTextarea,
    IonIcon,
    IonFab,
    IonFabButton,
    IonChip,
    IonGrid,
    IonRow,
    IonCol,
    IonSpinner,
    IonItem,
    IonLabel,
    IonButton
  },
  data() {
    return {
      message: '',
      mediaRecorder: null,
      audioChunks: null,
      speechAvailable: false
    };
  },
  computed: {
    partnersName() {
      return this.$store.state.partnersName;
    }
  },
  methods: {
    async recordSentence() {
      const speechAvailable = await this.checkSpeech();

      if (speechAvailable) {
        SpeechRecognition.startListening({matches: 1}).subscribe(
          matches => {
            const space = this.message.length == 0 ? '' : ' ';
            this.message = this.message + space + matches;
          },
          onerror => console.error(onerror));
      }
    },
    previewTextRecording() {
      this.$store.commit('setMessage', this.message);
      this.message = '';
      
      this.$router.push('/preview');
    },
    async checkSpeech() {
      try {
        const available = await SpeechRecognition.isRecognitionAvailable();
        const hasPermission = await SpeechRecognition.hasPermission();
        return available && hasPermission;
      } catch(error) {
        console.error('speech recognition check failed', error);
        return false;
      }
    }
  },
  ionViewWillEnter() {
    this.checkSpeech()
      .then(speechAvailable => {
        console.log('speech available: ' + speechAvailable)
        this.speechAvailable = speechAvailable;
      })
      .catch(error => console.error(error));
  }
});
</script>

<style scoped>
ion-chip {
  pointer-events: none;
}

.upper {
  text-transform: uppercase;
}
</style>
