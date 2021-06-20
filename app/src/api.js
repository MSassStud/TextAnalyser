import { server } from '@/config.js'

const host = server();

/**
 * @param {string} id a UUID
 * @param {string} username
 */
async function getMessage(id, username) {
    return fetch(`http://${host}/messages/${id}?user=${username}`);
}

/**
 * @param {string} username 
 * @param {string} id a UUID
 */
async function getTopic(username, id) {
    return fetch(`http://${host}/users/${username}/topics/${id}`);
}

/**
  * @param {string} username 
 * @param {*} topic 
 */
 async function saveTopic(username, topic) {
    if (topic.id) {
        return fetch(`http://${host}/users/${username}/topics/${topic.id}`, {
            method: 'PUT',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(topic)
        });
    }

    return fetch(`http://${host}/users/${username}/topics`, {
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
    return fetch(`http://${host}/users/${username}/topics`);
}

export {
    getMessage,
    getTopics,
    getTopic,
    saveTopic
}
