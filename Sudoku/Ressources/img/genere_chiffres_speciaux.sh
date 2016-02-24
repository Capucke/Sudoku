#! /bin/bash
set -x

# f69137280

# 48 ANIMAUX (EN COMPTANT CASE VIDE)

traiteImgCopiees(){
	curr_dossier="$1"
	img_src="$2"
	chemin_original="$3"
	cd "$chemin_original"
	cd "$curr_dossier"
	for image in $img_src
	do
		composite -gravity center "$image" "0.png" "$image"
	done
	cd "$chemin_original"
}

copieImg(){
	src="$1"
	dest="$2"
	
	orig=$(pwd)
	cd "$dest"
	pwd
	rm -rf *
	cd "$orig"
	cp "$src"*.png "$dest"
}

copieImgResize100(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="70x70\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
	convert -resize 125% "$src""$image" "$dest""$image"
	done
}

copieImgResize90(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="70x70\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
	convert -resize 113s% "$src""$image" "$dest""$image"
	done
}

copieImgResize80(){
	#img_src="$1"
	#src="$2"
	#dest="$3"
	copieImg "$2" "$3"
}

copieImgResize70(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="70x70\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
	convert -resize 87% "$src""$image" "$dest""$image"
	done
}

copieImgResize60(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="60x60\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
		convert -resize 75% "$src""$image" "$dest""$image"
	done
}

copieImgResize50(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="50x50\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
		convert -resize 63% "$src""$image" "$dest""$image"
		echo "Image copiée : ""$image"
	done
}

copieImgResize40(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="40x40\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
		convert -resize 50% "$src""$image" "$dest""$image"
	done
}

copieImgResize30(){
	img_src="$1"
	src="$2"
	dest="$3"
	
	# param="30x30\\!"
	
	orig=$(pwd)
	cd "$cur_dossier"
	pwd
	rm -rf *
	cd "$orig"
	for image in $img_src
	do
		convert -resize 38% "$src""$image" "$dest""$image"
	done
}


# Déclaration des tableaux et variables
chemin_base=$(pwd)
dossier_src="emoji_80px/"

declare -a taille
declare -a string_resize
declare -a dossier

taille[1]=100
taille[2]=90
taille[3]=80
taille[4]=70
taille[5]=60
taille[6]=50
taille[7]=40
taille[8]=30


string_resize[1]="100x100\\!"
string_resize[2]="90x90\\!"
string_resize[3]="80x80\\!"
string_resize[4]="70x70\\!"
string_resize[5]="60x60\\!"
string_resize[6]="50x50\\!"
string_resize[7]="40x40\\!"
string_resize[8]="30x30\\!"

#for var1 in 1 2 3 4 5 6 7 8
#do
#	{string_resize[var1]}="${taille[$var1]}x${taille[$var1]}\\! "
#	echo "String resize ${taille[$var1]} ${string_resize[var1]}"
#done

dossier[1]="chiffres_100px/special/"
dossier[2]="chiffres_90px/special/"
dossier[3]="chiffres_80px/special/"
dossier[4]="chiffres_70px/special/"
dossier[5]="chiffres_60px/special/"
dossier[6]="chiffres_50px/special/"
dossier[7]="chiffres_40px/special/"
dossier[8]="chiffres_30px/special/"

def="definitif/"
modif="modifiable/"

norm="normal/"
selec="selected/"

fond_def_norm="$dossier_src""Fonds/fond-def-normal"
fond_def_select="$dossier_src""Fonds/fond-def-select"
fond_modif_norm="$dossier_src""Fonds/fond-modif-normal"
fond_modif_select="$dossier_src""Fonds/fond-modif-select"

fond_def_norm_100="$fond_def_norm""_100px.png"
fond_def_norm_90="$fond_def_norm""_90px.png"
fond_def_norm_80="$fond_def_norm""_80px.png"
fond_def_norm_70="$fond_def_norm""_70px.png"
fond_def_norm_60="$fond_def_norm""_60px.png"
fond_def_norm_50="$fond_def_norm""_50px.png"
fond_def_norm_40="$fond_def_norm""_40px.png"
fond_def_norm_30="$fond_def_norm""_30px.png"

fond_def_select_100="$fond_def_select""_100px.png"
fond_def_select_90="$fond_def_select""_90px.png"
fond_def_select_80="$fond_def_select""_80px.png"
fond_def_select_70="$fond_def_select""_70px.png"
fond_def_select_60="$fond_def_select""_60px.png"
fond_def_select_50="$fond_def_select""_50px.png"
fond_def_select_40="$fond_def_select""_40px.png"
fond_def_select_30="$fond_def_select""_30px.png"

fond_modif_norm_100="$fond_modif_norm""_100px.png"
fond_modif_norm_90="$fond_modif_norm""_90px.png"
fond_modif_norm_80="$fond_modif_norm""_80px.png"
fond_modif_norm_70="$fond_modif_norm""_70px.png"
fond_modif_norm_60="$fond_modif_norm""_60px.png"
fond_modif_norm_50="$fond_modif_norm""_50px.png"
fond_modif_norm_40="$fond_modif_norm""_40px.png"
fond_modif_norm_30="$fond_modif_norm""_30px.png"

fond_modif_select_100="$fond_modif_select""_100px.png"
fond_modif_select_90="$fond_modif_select""_90px.png"
fond_modif_select_80="$fond_modif_select""_80px.png"
fond_modif_select_70="$fond_modif_select""_70px.png"
fond_modif_select_60="$fond_modif_select""_60px.png"
fond_modif_select_50="$fond_modif_select""_50px.png"
fond_modif_select_40="$fond_modif_select""_40px.png"
fond_modif_select_30="$fond_modif_select""_30px.png"

cd "$dossier_src"
images_src=$(find "./" -maxdepth 1 -name "*.png")
cd "$chemin_base"
images_src=$(echo "$images_src" | sed -e "s/.\///")
echo $images_src
echo

for var1 in 1 2 3 4 5 6 7 8
do
	# {string_resize[var1]}="${taille[$var1]}x${taille[$var1]}\\! "
	#
	if [ ${taille[$var1]} == 100 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_100" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_100" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_100" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_100" "$cur_dossier""0.png"
		eog "$fond_modif_select_100" &
		eog "$cur_dossier""0.png" &
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 90 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize90 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_90" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize90 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_90" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize90 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_90" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize90 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_90" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 80 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_80" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_80" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_80" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImg "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_80" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 70 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize70 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_70" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize70 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_70" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize70 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_70" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize70 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_70" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 60 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize60 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_60" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize60 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_60" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize60 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_60" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize60 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_60" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 50 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize50 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_50" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize50 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_50" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize50 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_50" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize50 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_50" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 40 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize40 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_40" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize40 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_40" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize40 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_40" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize40 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_40" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	elif [ ${taille[$var1]} == 30 ]
	then
		# definitif-normal
		cur_dossier="${dossier[$var1]}""$def""$norm"
		copieImgResize30 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_norm_30" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# definitif-selected
		cur_dossier="${dossier[$var1]}""$def""$selec"
		copieImgResize30 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_def_select_30" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-normal
		cur_dossier="${dossier[$var1]}""$modif""$norm"
		copieImgResize30 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_norm_30" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
		# modifiable-selected
		cur_dossier="${dossier[$var1]}""$modif""$selec"
		copieImgResize30 "$images_src" "$dossier_src" "$cur_dossier"
		cp "$fond_modif_select_30" "$cur_dossier""0.png"
		traiteImgCopiees "$cur_dossier" "$images_src" "$chemin_base"
		#
	else
		exit 1
	fi
done