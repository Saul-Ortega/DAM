#!/bin/sh
printf '\033c\033]0;%s\a' ProyectoGodot
base_path="$(dirname "$(realpath "$0")")"
"$base_path/ProyectoGodot.x86_64" "$@"
