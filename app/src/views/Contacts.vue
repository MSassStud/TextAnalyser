<template>
  <ion-page>
    <ion-content :fullscreen="true">
        <ion-list>
            <ion-list-header>Contacts</ion-list-header>

            <ion-item>
                <ion-label>Alice</ion-label>
            </ion-item>

            <ion-item>
                <ion-label>Bob</ion-label>
            </ion-item>

            <ion-item>
                <ion-label>Eve</ion-label>
            </ion-item>
        </ion-list>
    </ion-content>
  </ion-page>
</template>

<script>
import { IonContent, IonPage } from '@ionic/vue';
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Contacts',
  components: {
    IonContent,
    IonPage
  }
});
</script>

<style scoped>
</style>
