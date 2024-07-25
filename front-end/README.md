# React Setup

## Introduction
Doot is a fully featured premium chat app template in ReactJs with ReduxHooks and Redux Saga with Firebase backend with Google and Facebook authentication and developer-friendly codes.

> Note: You need to fill Firebase credentials in the .env.file and for Social ( Google / Facebook ) credentials in the src/config.js file.

## Prerequisites
Please follow the below steps to install and setup all prerequisites:

### Node.js
- Ensure you have Node.js installed & running on your computer. If you already have installed Node on your computer, you can skip this step if your existing node version is greater than 10.

#### Node version support policy
1. Supported Node.js versions vary by release, please consult the [releases page](https://nodejs.org/en/about/releases/).
2. Node versions that hit the end of life (https://github.com/nodejs/Release) will be removed from support at each code-base release (major, minor).
3. We will stop building binaries for unsupported releases, testing for breakages in dependency compatibility, but we will not block installations for those that want to support themselves.
4. If release notes require minor internal changes along with support from CI vendors (TravisCI, AppVeyor, GitHub Actions), we will open a single issue for interested parties to subscribe to, and close additional issues.

Below is a quick guide for minimum and maximum support supported version of node-sass:

| Node | Supported node-sass version | Node Module |
|------|------------------------------|-------------|
| Node 18 | 8.0+ | 108 |
| Node 16 | 6.0+ | 93 |
| Node 15 | 5.0+ | 88 |
| Node 14 | 4.14+, <5.0 | 83 |
| Node 13 | 4.13+, <5.0 | 79 |
| Node 12 | 4.12+, <5.0 | 72 |
| Node 11 | 4.10+, <5.0 | 67 |
| Node 10 | 4.9+, <5.0 | 64 |
| Node 9 | 4.5.3+, <5.0 | 57 |
| Node 8 | <5.0 | <57 |

### Git
Make sure to have Git installed globally & running on your computer. If you already have installed git on your computer, you can skip this step.

## Installation
To set up the chat app, follow the below-mentioned steps:

### Install Prerequisites
Make sure to have all the above prerequisites installed & running on your computer.

After you finished with the above steps, you can run the following commands in the terminal/command prompt from the root directory of the project to run the project locally or build for production use:

| Command | Description |
|---------|-------------|
| `yarn install` | This would install all the required dependencies in the node_modules folder. |
| `yarn start` | Runs the project locally, starts the development server, and watches for any changes in your code. The development server is accessible at [http://localhost:3000](http://localhost:3000). |
| `yarn build` | Generates a /build directory with all the production files. |

## Tip
**SCSS:** We suggest you do not change any scss files from the scss/base/ to scss/theme.scss folders because getting new updates will break your SCSS changes if any you have made. We strongly suggest you create a new custom.scss file and use that instead of overwriting any theme's custom scss files.
