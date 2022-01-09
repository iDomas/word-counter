FROM node:latest as node_build

WORKDIR /tmp/site

COPY ./ /tmp/site/

RUN npm install
RUN npm run build


FROM nginx:latest

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=node_build /tmp/site/dist/word-counter-frontend /usr/share/nginx/

EXPOSE 80





