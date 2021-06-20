import { createRouter, createWebHistory } from '@ionic/vue-router';
import Home from '../views/Home.vue'
import Contacts from '../views/Contacts.vue'
import Conversation from '../views/Conversation.vue'
import Record from '../views/Record.vue'
import Preview from '../views/Preview.vue'
import RecordSpeech from '../views/RecordSpeech.vue'
import RecordSpeechAndroid from '../views/RecordSpeechAndroid.vue'
import Message from '../views/Message.vue'
import Topic from '../views/Topic.vue'
import Topics from '../views/Topics.vue'

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
    path: '/recordSpeechAndroid',
    name: 'RecordSpeechAndroid',
    component: RecordSpeechAndroid
  },
  {
    path: '/preview',
    name: 'Preview',
    component: Preview
  },
  {
    path: '/message',
    name: 'Message',
    component: Message
  },
  {
    path: '/topic/:id?',
    name: 'Topic',
    component: Topic
  },
  {
    path: '/topics',
    name: 'Topics',
    component: Topics
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
