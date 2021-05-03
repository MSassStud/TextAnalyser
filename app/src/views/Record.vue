<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ partnersName }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-textarea v-if="recording" v-model="message"></ion-textarea>
    </ion-content>

    <ion-footer>
      <ion-button v-if="!recording" expand="full" @click="record">
        Start recording
      </ion-button>
      <ion-button v-if="recording" expand="full" @click="previewRecording">
        Stop recording
      </ion-button>
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
  IonFooter,
  IonTextarea,
  IonButton
} from "@ionic/vue";
import { defineComponent } from "vue";
// import { useRouter } from 'vue-router';

export default defineComponent({
  name: "Record",
  components: {
    IonContent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonFooter,
    IonTextarea,
    IonButton
  },
  // emits: ['recordedMessage'],
  // setup() {
  //   const router = useRouter();
  //   return { router };
  // },
  data() {
    return {
      recording: false,
      message: '',
    };
  },
  computed: {
    partnersName() {
      return this.$store.state.partnersName;
    }
  },
  methods: {
    record() {
      this.recording = true;
    },
    previewRecording() {
      console.log('sending PRE');
      // this.$emit('recordedMessage', this.message);
      this.$store.commit('setMessage', this.message);
      this.$router.push('/preview');
    }
  },
});
</script>

<style scoped>
</style>
