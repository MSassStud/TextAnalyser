<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-button router-link="/conversation2">
            <ion-icon name="arrow-back-outline"></ion-icon>
          </ion-button>
        </ion-buttons>
        <ion-title>Topics</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <div class="ion-padding">
        <ion-text>Messages you receive will be searched for mentions of the following topics.</ion-text>
      </div>

      <ion-item v-for="topic in topics" :key="topic.id">
        <ion-label class="topic">{{ topic.emoji }} {{ topic.title }}</ion-label>
        <ion-button slot="end" @click="editTopic(topic.id)">
          <ion-icon name="build-outline" class="inlineIcon"></ion-icon> Edit
        </ion-button>
      </ion-item>
    </ion-content>

    <ion-footer>
      <ion-toolbar>
        <ion-button expand="block" @click="addTopic">Add topic</ion-button>
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
  IonButtons,
  IonItem,
  IonButton,
  IonFooter,
  IonLabel,
  IonIcon,
  IonText
} from "@ionic/vue";
import { defineComponent } from "vue";
import * as Api from '@/api.js';
import {
  buildOutline
} from 'ionicons/icons';
import { addIcons } from 'ionicons';

addIcons({ 'build-outline': buildOutline });

export default defineComponent({
  name: "Topics",
  components: {
    IonPage,
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonButtons,
    IonItem,
    IonButton,
    IonFooter,
    IonLabel,
    IonIcon,
    IonText
  },
  data() {
    return {
      topics: []
    };
  },
  computed: {
    username() {
      return this.$store.state.ownName;
    }
  },
  methods: {
    addTopic() {
      this.$router.push('/topic');
    },
    editTopic(id) {
      this.$router.push({
        name: 'Topic',
        params: { id: id }
      });
    }
  },
  ionViewDidEnter() {
    Api.getTopics(this.username)
      .then(response => response.json())
      .then(topics => {
        this.topics = topics.sort((a, b) => a.title > b.title);
      });
  }
});
</script>

<style scoped>
.topic {
  font-size: 1.5em;
}

.inlineIcon {
  margin-right: 0.3em;
}
</style>
