import { APIClient } from "./apiCore";
import * as url from "./urls";
import {CREATE_CHANNEL_V2} from "./urls";

const api = new APIClient();

const getFavourites = () => {
  return api.get(url.GET_FAVOURITES);
};

const getDirectMessages = () => { // modified
  return api.get(url.GET_DIRECT_MESSAGES_V2);
};
const getChannels = () => {
  return api.get(url.GET_CHANNELS);
};

const addContacts = (contacts: Array<string | number>) => {
  return api.create(url.ADD_CONTACTS, contacts);
};

/*
- Hàm này dùng để tạo kênh chat khi gửi lời mời thêm contact
 */
const createChannel = (data: object) => {
  return api.create(url.CREATE_CHANNEL_V2, data);
};

const getChatUserDetails = (id: string | number) => {
  return api.get(url.GET_CHAT_USER_DETAILS_V2 + "/" + id);
};

const getChatUserConversations = (id: string | number) => {
  const mappedUrl = url.GET_CHAT_USER_CONVERSATIONS_2.replace("{contactId}",id.toString());
  console.log(mappedUrl)
  return api.get(mappedUrl);
};

const sendMessage = (data: object,id: string | number) => {
  const mappedUrl = url.SEND_MESSAGE_V2.replace("{chatId}",id.toString());
  console.log(id)
  console.log(mappedUrl)
  return api.create(mappedUrl, data);

};

const receiveMessage = (id: string | number) => {
  // return api.update(url.RECEIVE_MESSAGE + "/" + id, { params: { id } });
  return getChatUserConversations(id);
};

const readMessage = (id: string | number) => {
  return api.update(url.READ_MESSAGE + "/" + id, { params: { id } });
};

const receiveMessageFromUser = (id: string | number) => {
  return api.get(url.RECEIVE_MESSAGE_FROM_USER + "/" + id, {
    params: { id },
  });
};

const deleteMessage = (userId: number | string, messageId: number | string) => {
  const mappedUrl = url.DELETE_MESSAGE_V2.replace("{id}",messageId.toString());
  console.log(mappedUrl)
  return api.delete(mappedUrl);
};

const forwardMessage = (data: object) => {
  return api.create(url.FORWARD_MESSAGE, data);
};

const deleteUserMessages = (userId: number | string) => {
  return api.delete(url.DELETE_USER_MESSAGES + "/" + userId, {
    params: { userId },
  });
};

const getChannelDetails = (id: string | number) => {
  return api.get(url.GET_CHANNEL_DETAILS + "/" + id, { params: { id } });
};

const toggleFavouriteContact = (id: string | number) => {
  return api.update(url.TOGGLE_FAVOURITE_CONTACT + "/" + id, {
    params: { id },
  });
};

/*
archive
*/
const getArchiveContact = () => {
  return api.get(url.GET_ARCHIVE_CONTACT);
};

const toggleArchiveContact = (id: string | number) => {
  return api.update(url.TOGGLE_ARCHIVE_CONTACT + "/" + id, { params: { id } });
};

const readConversation = (id: string | number) => {
  return api.update(url.READ_CONVERSATION + "/" + id, { params: { id } });
};

const deleteImage = (
  userId: number | string,
  messageId: number | string,
  imageId: number | string
) => {
  return api.delete(url.DELETE_IMAGE + "/" + userId + "/" + messageId, {
    params: { userId, messageId, imageId },
  });
};

export {
  getFavourites,
  getDirectMessages,
  getChannels,
  addContacts,
  createChannel,
  getChatUserDetails,
  getChatUserConversations,
  sendMessage,
  receiveMessage,
  readMessage,
  receiveMessageFromUser,
  deleteMessage,
  forwardMessage,
  deleteUserMessages,
  getChannelDetails,
  toggleFavouriteContact,
  getArchiveContact,
  toggleArchiveContact,
  readConversation,
  deleteImage,
};
