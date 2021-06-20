<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Topic</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <ion-grid>
        <ion-row>
          <ion-col size="3">
            <ion-item>
              <ion-label position="stacked">Emoji</ion-label>
              <ion-button color="light" @click="openEmojiSelection">{{ emoji }}</ion-button>
            </ion-item>
          </ion-col>
          <ion-col>
            <ion-item>
              <ion-label position="stacked">Title</ion-label>
              <ion-input type="text" v-model="title"></ion-input>
            </ion-item>
          </ion-col>
        </ion-row>
        <ion-row>
          <ion-col>
            <ion-item>
              <ion-label position="stacked">Keywords</ion-label>
              <ion-textarea v-model="keywords"></ion-textarea>
            </ion-item>
          </ion-col>
        </ion-row>
      </ion-grid>
    </ion-content>

    <ion-footer>
      <ion-toolbar>
        <ion-button expand="block" @click="applyChanges">Apply</ion-button>
      </ion-toolbar>
    </ion-footer>
  </ion-page>
</template>

<script>
import {
  IonPage,
  IonContent,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonGrid,
  IonRow,
  IonCol,
  IonItem,
  IonButton,
  IonInput,
  IonTextarea,
  IonFooter,
  IonLabel,
  modalController
} from "@ionic/vue";
import { defineComponent } from "vue";
import * as Api from '@/api.js';
import EmojiSelection from '@/views/EmojiSelection.vue';

export default defineComponent({
  name: "Topic",
  components: {
    IonPage,
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonGrid,
    IonRow,
    IonCol,
    IonItem,
    IonButton,
    IonInput,
    IonTextarea,
    IonFooter,
    IonLabel
  },
  data() {
    return {
      emoji: '',
      title: '',
      keywords: ''
    };
  },
  computed: {
    username() {
      return this.$store.state.ownName;
    }
  },
  methods: {
    async openEmojiSelection() {
      const modal = await modalController.create({
        component: EmojiSelection,
        componentProps: {
          emoji: {
            emoji: this.emoji,
            time: 0
          }
        }
      });

      modal.onDidDismiss()
        .then(returned => returned.data)
        .then(data => {
          if (data.action) {
            if (data.action.name == 'select') {
              this.emoji = data.action.selection;
            } else if (data.action.name == 'delete') {
              this.emoji = '';
            }
          }
        });

        return modal.present();
    },
    applyChanges() {
      const topic = {
        emoji: this.emoji,
        title: this.title,
        keywords: this.keywords
      };

      const id = this.$route.params.id;
      if (id) {
        topic.id = id;
      }

      Api.saveTopic(this.username, topic)
        .then(() => this.$router.push('/topics'));
    }
  },
  ionViewDidEnter() {
    const id = this.$route.params.id;
    if (id) {
      Api.getTopic(this.username, id)
        .then(response => response.json())
        .then(topic => {
          this.emoji = topic.emoji;
          this.title = topic.title;
          this.keywords = topic.keywords;
        });
    }
  }
});
</script>

<style scoped>
</style>
