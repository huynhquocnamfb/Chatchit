//auth
const AUTH_BASE_ENDPOINT = "/api/v1/auth" // edited
// export const POST_FAKE_LOGIN = "/post-fake-login";
export const POST_FAKE_LOGIN = "/api/v1/auth/sign-in";
// export const POST_FAKE_JWT_LOGIN = "/post-jwt-login";
export const POST_FAKE_JWT_LOGIN = "/api/v1/auth/sign-in";

export const POST_FAKE_PASSWORD_FORGET = "/fake-forget-pwd";
export const POST_FAKE_JWT_PASSWORD_FORGET = "/jwt-forget-pwd";
export const SOCIAL_LOGIN = "/social-login";
//export const JWT_REGISTER = AUTH_BASE_ENDPOINT+"/sign-up"     //"/post-jwt-register";
export const JWT_REGISTER = "/api/v1/users"     //"/post-jwt-register";
export const POST_FAKE_REGISTER = "/post-fake-register";

export const USER_CHANGE_PASSWORD = "/user-change-password";

// profile & settings
export const GET_PROFILE_DETAILS = "/user-details"; // modified
export const GET_MY_PROFILE = "/api/v1/users/me/profile"; // modified
export const GET_USER_SETTINGS = "/user-settings";
export const GET_USER_SETTINGS_2 = "/api/v1/users/me/profile";
export const UPDATE_ETTINGS = "/update-user-settings";

// contacts
export const GET_CONTACTS = "";
export const GET_CONTACTS_2 = "/api/v1/contacts";
export const SEARCH_CONTACTS_2 = "/api/v1/contacts/search";
export const INVITE_CONTACT = "";

export const INVITE_CONTACT_2 = "/api/v1/contacts";
// calls
export const GET_CALLS_LIST = "/calls-list";

// bookmarks
export const GET_BOOKMARKS_LIST = "/bookmarks-list";
export const DELETE_BOOKMARK = "/bookmarks-delete";
export const UPDATE_BOOKMARK = "/bookmarks-update";

// chats
export const GET_FAVOURITES = "/api/v1/contacts?type=favourite"; // modified
export const GET_DIRECT_MESSAGES = "/get-direct-messages";
export const GET_DIRECT_MESSAGES_V2 = GET_CONTACTS_2;
export const GET_CHANNELS = "/get-channles";
export const ADD_CONTACTS = "/add-contact";
export const CREATE_CHANNEL = "/create-channel";

export const CREATE_CHANNEL_V2 = "/api/v1/chat";
export const GET_CHAT_USER_DETAILS = "/get-user-details";
export const GET_CHAT_USER_DETAILS_V2 = "/api/v1/users";
export const GET_CHAT_USER_CONVERSATIONS = "/get-user-conversations";
export const GET_CHAT_USER_CONVERSATIONS_2 = "/api/v1/conversations/{contactId}";
export const SEND_MESSAGE = "/send-message";
export const SEND_MESSAGE_V2 = "/api/v1/chats/{chatId}/messages";
export const RECEIVE_MESSAGE = "/receive-message";
export const RECEIVE_MESSAGE_V2 = "/api/v1/chats/{chatId}/messages";
export const READ_MESSAGE = "/read-message";
export const RECEIVE_MESSAGE_FROM_USER = "/receive-message-from-user";
export const DELETE_MESSAGE = "/delete-message";
export const DELETE_MESSAGE_V2 = "/api/v1/messages/{id}";
export const FORWARD_MESSAGE = "/forward-message";
export const DELETE_USER_MESSAGES = "/delete-user-messages";
export const TOGGLE_FAVOURITE_CONTACT = "/toggle-favourite-contact";
export const GET_ARCHIVE_CONTACT = "/get-archive-contacts";
export const TOGGLE_ARCHIVE_CONTACT = "/toggle-archive-contact";
export const READ_CONVERSATION = "/read-conversation";
export const DELETE_IMAGE = "/user-delete-img";

// groups
export const GET_CHANNEL_DETAILS = "/get-channel-details";

export const GET_AUTH_USER_DETAILS = "/api/v1/users/me"