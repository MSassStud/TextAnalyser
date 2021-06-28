import { getServer } from '@/config.js'

// const host = getServer();

function host() {
    return getServer();
}

/**
 * @param {string} id a UUID
 * @param {string} username
 */
async function getMessage(id, username) {
    return fetch(`http://${host()}/messages/${id}?user=${username}`);
}

/**
 * @param {string} username 
 * @param {string} partnersName 
 */
async function getConversation(username, partnersName) {
    return fetch(`http://${host()}/conversations?a=${username}&b=${partnersName}`);
}

/**
 * @param {string} username 
 * @param {string} partnersName 
 */
 async function getConversation2(username, partnersName) {
    return fetch(`http://${host()}/conversations2?a=${username}&b=${partnersName}`);
}

/**
 * @param {string} username 
 * @param {string} id a UUID
 */
async function getTopic(username, id) {
    return fetch(`http://${host()}/users/${username}/topics/${id}`);
}

/**
  * @param {string} username 
 * @param {*} topic 
 */
 async function saveTopic(username, topic) {
    if (topic.id) {
        return fetch(`http://${host()}/users/${username}/topics/${topic.id}`, {
            method: 'PUT',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(topic)
        });
    }

    return fetch(`http://${host()}/users/${username}/topics`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(topic)
    });
}

/**
 * @param {string} username 
 */
async function getTopics(username) {
    return fetch(`http://${host()}/users/${username}/topics`);
}

export {
    getConversation,
    getConversation2,
    getMessage,
    getTopics,
    getTopic,
    saveTopic
}
