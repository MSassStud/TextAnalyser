<template>
  <div>
    <ion-item>
      <ion-label>Sign in as:</ion-label>
      <ion-input></ion-input>
    </ion-item>
    <ion-button expand="full" router-link="/contacts">Sign in</ion-button>
  </div>
</template>

<script>
import { IonLabel, IonInput, IonButton } from '@ionic/vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'SignIn',
  components: {
    IonLabel,
    IonInput,
    IonButton
  }
});
</script>

<style scoped>
</style>