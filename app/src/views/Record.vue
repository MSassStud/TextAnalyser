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
              <ion-textarea v-if="recording" v-model="message" auto-grow="true"></ion-textarea>
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
  IonLabel
} from "@ionic/vue";
import { defineComponent } from "vue";
import { addIcons } from 'ionicons';
import { micOutline, stop } from 'ionicons/icons';
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
    IonLabel
  },
  data() {
    return {
      recording: true,
      message: '',
      mediaRecorder: null,
      audioChunks: null
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
    // recordSpeech() {
    //   this.recording = true;

    //   navigator.mediaDevices.getUserMedia({audio: true, video: false})
    //     .then(stream => {
    //       this.mediaRecorder = new MediaRecorder(stream);
    //       this.mediaRecorder.start();

    //       this.audioChunks = [];
    //       console.log(this.mediaRecorder);

    //       this.mediaRecorder.addEventListener("dataavailable", event => {
    //         this.audioChunks.push(event.data);
    //       });

    //       this.mediaRecorder.addEventListener("stop", () => {
    //         const blob = new Blob(this.audioChunks);
    //         const url = URL.createObjectURL(blob);
    //         const audio = new Audio(url);
    //         audio.play();
    //         console.log("playing");

    //         const reader = new FileReader();
    //         reader.readAsDataURL(blob);
    //         reader.onload = () => {
    //           const base64Audio = reader.result.split(',')[1];
    //           console.log(base64Audio);

    //           fetch('http://localhost:8080/recording', {
    //             method: 'POST',
    //             headers: {
    //               'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({ recording: base64Audio })
    //           });
    //         };
    //       });
    //     });
    // },
    // previewRecording() {
    //   this.mediaRecorder.stop();

    //   this.$store.commit('setMessage', this.message);

    //   this.recording = false;
    //   this.message = '';
    // },
    previewTextRecording() {
      this.$store.commit('setMessage', this.message);
      this.message = '';
      
      this.$router.push('/preview');
    }
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
