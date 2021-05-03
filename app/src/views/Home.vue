<template>
  <ion-page>
    <ion-content :fullscreen="true">
      <ion-label>Your name</ion-label>
      <ion-input v-model="ownName"></ion-input>
      <ion-label>Partner's name</ion-label>
      <ion-input v-model="partnersName"></ion-input>
      <ion-button expand="full" :disabled="anyNameEmpty" @click="startConversation">Start conversation</ion-button>
    </ion-content>
  </ion-page>
</template>

<script>
import { IonContent, IonPage, IonInput, IonLabel, IonButton } from "@ionic/vue";
import { defineComponent } from "vue";

export default defineComponent({
  name: "Home",
  components: {
    IonContent,
    IonPage,
    IonInput,
    IonLabel,
    IonButton,
  },
  data() {
    return {
      ownName: '',
      partnersName: '',
    };
  },
  computed: {
    anyNameEmpty() {
      return (
        this.ownName.trim().length == 0 || this.partnersName.trim().length == 0
      );
    },
  },
  methods: {
    startConversation() {
      this.$store.commit('setNames', {
        own: this.ownName,
        partner: this.partnersName
      });
      this.$router.push('/conversation');
    }
  }
});
</script>

<style scoped>
#container {
  text-align: center;

  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

#container strong {
  font-size: 20px;
  line-height: 26px;
}

#container p {
  font-size: 16px;
  line-height: 22px;

  color: #8c8c8c;

  margin: 0;
}

#container a {
  text-decoration: none;
}
</style>