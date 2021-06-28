<template>
  <ion-page>
    <ion-content :fullscreen="true">
      <ion-grid style="margin-top: 50%">
        <ion-row>
          <ion-col>
            <ion-item>
              <ion-label position="stacked" class="upper">Your name</ion-label>
              <ion-input v-model="ownName"></ion-input>
            </ion-item>
            <ion-item>
              <ion-label position="stacked" class="upper">Partner's name</ion-label>
              <ion-input v-model="partnersName"></ion-input>
            </ion-item>
            <ion-item>
              <ion-label position="stacked" class="upper">Server</ion-label>
              <ion-input v-model="server"></ion-input>
            </ion-item>
          </ion-col>
          <ion-col size="12">
            <ion-button expand="block" :disabled="anyNameEmpty" @click="startConversation">Start conversation</ion-button>
          </ion-col>
        </ion-row>
      </ion-grid>

    </ion-content>
  </ion-page>
</template>

<script>
import { IonContent, IonPage, IonInput, IonLabel, IonButton, IonGrid, IonRow, IonCol,
IonItem
} from "@ionic/vue";
import { defineComponent } from "vue";
import { getServer, setServer } from '@/config.js';

export default defineComponent({
  name: "Home",
  components: {
    IonContent,
    IonPage,
    IonInput,
    IonLabel,
    IonButton,
    IonGrid,
    IonRow,
    IonCol,
    IonItem
  },
  data() {
    return {
      ownName: '',
      partnersName: '',
      server: getServer()
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

      setServer(this.server);

      this.$router.push('/conversation2');
    }
  }
});
</script>

<style scoped>
.upper {
  text-transform: uppercase;
}

#wrapper {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

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