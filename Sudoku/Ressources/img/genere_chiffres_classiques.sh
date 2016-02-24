#! /bin/bash

set -x


copieImg(){
	src="$1"
	dest="$2"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	cp "$src"*.png "$dest"
}


copieImgResize100(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="100x100\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 167% "$src""$image" "$dest""$image"
	done
}

copieImgResize90(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="90x90\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 150% "$src""$image" "$dest""$image"
	done
}

copieImgResize80(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="80x80\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 133% "$src""$image" "$dest""$image"
	done
}

copieImgResize70(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="70x70\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 116% "$src""$image" "$dest""$image"
	done
}

copieImgResize60(){
	# Rien à faire
	exit 0
}

copieImgResize50(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="50x50\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 83% "$src""$image" "$dest""$image"
	done
}

copieImgResize40(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="40x40\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 67% "$src""$image" "$dest""$image"
	done
}

copieImgResize30(){
	img_list="$1"
	src="$2"
	dest="$3"

	# param="30x30\\!"

	orig=$(pwd)
	cd "$dest"
	#pwd
	rm -rf *
	cd "$orig"
	for image in $img_list
	do
		convert -resize 50% "$src""$image" "$dest""$image"
	done
}


# Déclaration des tableaux et variables
chemin_base=$(pwd)
dossier_src="chiffres_60px/classique/"

declare -a taille
declare -a string_resize
declare -a dossier
declare -a sous_dossier
declare -a sous_sous_dossier

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

dossier[1]="chiffres_100px/classique/"
dossier[2]="chiffres_90px/classique/"
dossier[3]="chiffres_80px/classique/"
dossier[4]="chiffres_70px/classique/"
dossier[5]="chiffres_60px/classique/"
dossier[6]="chiffres_50px/classique/"
dossier[7]="chiffres_40px/classique/"
dossier[8]="chiffres_30px/classique/"

sous_dossier[1]="definitif/"
sous_dossier[2]="modifiable/"

sous_sous_dossier[1]="normal/"
sous_sous_dossier[2]="selected/"

cd "$dossier_src""${sous_dossier[1]}""${sous_sous_dossier[1]}"
images_src=$(find "./" -maxdepth 1 -name "*.png")
cd "$chemin_base"
images_src=$(echo "$images_src" | sort | sed -e "s/.\///")

echo $images_src
echo


fullCopieImgResize100(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize100 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize90(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize90 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize80(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize80 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize70(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize70 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize60(){
	# RIEN A FAIRE
	echo
	#liste_img="$1"
	#src_racine="$2"
	#dest_racine="$3"
	#
	#for var2 in 1 2
	#do
	#	for var3 in 1 2
	#	do
	#		src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
	#		dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
	#		copieImgResize60 "$liste_img" "$src_folder" "$dst_folder"
	#	done
	#done
}

fullCopieImgResize50(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize50 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize40(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize40 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}

fullCopieImgResize30(){
	liste_img="$1"
	src_racine="$2"
	dest_racine="$3"

	for var2 in 1 2
	do
		for var3 in 1 2
		do
			src_folder="$src_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			dst_folder="$dest_racine""${sous_dossier[$var2]}""${sous_sous_dossier[$var3]}"
			copieImgResize30 "$liste_img" "$src_folder" "$dst_folder"
		done
	done
}


for var1 in 1 2 3 4 5 6 7 8
do
	#
	if [ ${taille[$var1]} == 100 ]
	then
		fullCopieImgResize100 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 90 ]
	then
		fullCopieImgResize90 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 80 ]
	then
		fullCopieImgResize80 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 70 ]
	then
		fullCopieImgResize70 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 60 ]
	then
		# rien à faire car 60px est le dossier de base
		echo "on ne fait rien"
	elif [ ${taille[$var1]} == 50 ]
	then
		fullCopieImgResize50 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 40 ]
	then
		fullCopieImgResize40 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	elif [ ${taille[$var1]} == 30 ]
	then
		fullCopieImgResize30 "$images_src" "$dossier_src" "${dossier[$var1]}"
		#
	else
		exit 1
	fi
done