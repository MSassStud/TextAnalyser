import { createRouter, createWebHistory } from '@ionic/vue-router';
import Home from '../views/Home.vue'
import Contacts from '../views/Contacts.vue'
import Conversation from '../views/Conversation.vue'
import Record from '../views/Record.vue'
import Preview from '../views/Preview.vue'
import RecordSpeech from '../views/RecordSpeech.vue'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/contacts',
    name: 'Contacts',
    component: Contacts
  },
  {
    path: '/conversation',
    name: 'Conversation',
    component: Conversation
  },
  {
    path: '/record',
    name: 'Record',
    component: Record
  },
  {
    path: '/recordSpeech',
    name: 'RecordSpeech',
    component: RecordSpeech
  },
  {
    path: '/preview',
    name: 'Preview',
    component: Preview
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
