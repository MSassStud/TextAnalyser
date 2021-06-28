<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <div>{{ errors }}</div>
      <ion-grid>
        <ion-row class="ion-align-items-center ion-justify-content-center">
          <ion-col style="text-align: center">
            <ion-chip color="danger">
              <ion-icon name="mic-outline" style="font-size: 2em"></ion-icon>
              <ion-spinner name="dots"></ion-spinner>
            </ion-chip>
          </ion-col>
        </ion-row>
      </ion-grid>

      <ion-fab slot="fixed" vertical="bottom" horizontal="center">
        <ion-fab-button @click="stopRecording">
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
  IonIcon,
  IonFab,
  IonFabButton,
  IonChip,
  IonGrid,
  IonRow,
  IonCol
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { micOutline, stop } from 'ionicons/icons';
import { SpeechRecognition } from '@ionic-native/speech-recognition';
import { getServer } from '../config.js';

addIcons({ 'mic-outline': micOutline, stop });

export default defineComponent({
  name: "RecordSpeech",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonIcon,
    IonFab,
    IonFabButton,
    IonChip,
    IonGrid,
    IonRow,
    IonCol
  },
  data() {
    return {
      recording: true,
      message: '',
      mediaRecorder: null,
      audioChunks: null,
      errors: ''
    };
  },
  computed: {
    partnersName() {
      return this.$store.state.partnersName;
    }
  },
  methods: {
    recordText() {
      this.recording = true;
    },
    recordSpeech() {
      this.recording = true;

      navigator.mediaDevices.getUserMedia({audio: true, video: false})
        .then(stream => {
          this.mediaRecorder = new MediaRecorder(stream);
          this.mediaRecorder.start();

          this.audioChunks = [];
          console.log(this.mediaRecorder);

          this.mediaRecorder.addEventListener("dataavailable", event => {
            this.audioChunks.push(event.data);
          });

          this.mediaRecorder.addEventListener("stop", () => {
            const blob = new Blob(this.audioChunks);

            const reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onload = () => {
              const base64Audio = reader.result.split(',')[1];
              console.log(base64Audio);

              fetch('http://' + getServer() + '/recording', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify({ recording: base64Audio })
              })
                .then(response => response.json())
                .then(data => this.message = data.message)
                .then(() => this.previewRecording());
            };
          });
        });
    },
    stopRecording() {
      this.mediaRecorder.stop();
    },
    recordSpeech2() {
      console.log('speech');
      // SpeechRecognition.isRecognitionAvailable().then(available => console.log('available: ' + available));
      // SpeechRecognition.hasPermission().then(hasPermission => console.log('hasPermission: ' + hasPermission));
      // SpeechRecognition.startListening().subscribe(matches => console.log(matches), onerror => console.log('error:', onerror));
      SpeechRecognition.isRecognitionAvailable().then(available => this.errors = this.errors + 'available: ' + available);
      SpeechRecognition.hasPermission().then(hasPermission => this.errors = this.errors + 'hasPermission: ' + hasPermission);
      SpeechRecognition.startListening().subscribe(matches => this.errors = this.errors + matches, onerror => this.errors = this.errors + 'error:' + onerror);
    },
    previewRecording() {
      this.$store.commit('setMessage', this.message);

      this.recording = false;
      this.message = '';

      this.$router.push('/preview');
    },
    previewTextRecording() {
      this.$store.commit('setMessage', this.message);
      this.message = '';
      
      this.$router.push('/preview');
    }
  },
  ionViewDidEnter() {
    this.recordSpeech2();
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
