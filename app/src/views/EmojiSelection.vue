<template>
  <ion-header>
    <ion-toolbar>
      <ion-title>Select emoji</ion-title>
      <ion-buttons slot="end">
        <ion-button @click="closeModal">Close</ion-button>
      </ion-buttons>
    </ion-toolbar>
  </ion-header>

  <ion-content>
    <div>
      <span v-for="(emoji, index) in emojiRef" :key="index" @click="select(emoji)">
        {{ emoji }}
      </span>
    </div>
    <div>
      <ion-button @click="delete">Delete</ion-button>
    </div>
  </ion-content>
</template>

<script>
import {
  IonContent,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonButton,
  modalController
} from "@ionic/vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "EmojiSelection",
  emits: ['selectedEmoji'],
  props: {
    emoji: Object
  },
  components: {
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonButtons,
    IonButton
  },
  data() {
    return {
      emojiRef: ['☺️', '😊', '😇', '🙂', '🙃', '😔', '😟', '😕', '🙁', '☹️'],
      selection: null,
      action: null
    };
  },
  methods: {
    select(emoji) {
      console.log('selected ' + emoji);
      this.selection = emoji;
      this.action = { name: 'select', selection: emoji };

      // const prev = this.$store.state.selectedEmoji;
      // this.$store.commit('setSelectedEmoji', { id: prev.id, emoji: emoji, time: prev.time });

      // this.$emit('selectedEmoji', { emoji });
      this.closeModal();
    },
    delete() {
      this.action = { name: 'delete' };
      this.closeModal();
    },
    closeModal() {
      modalController.dismiss({
        prev: this.emoji,
        action: this.action
      });
    }
  }
});
</script>

<style scoped>
</style>
