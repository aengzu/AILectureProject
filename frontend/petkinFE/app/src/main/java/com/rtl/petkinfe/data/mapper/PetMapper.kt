package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.PetRegisterRequestDto
import com.rtl.petkinfe.data.remote.dto.PetRegisterResponseDto
import com.rtl.petkinfe.data.remote.dto.PetResponseDto
import com.rtl.petkinfe.domain.model.Pet

fun PetRegisterResponseDto.toDomainModel(): Pet {
    return Pet(
        id = petId,
        name = name,
        species = species,
        breed = breed,
        age = age,
        gender = gender,
        registerDate = registrationDate
    )
}

fun PetResponseDto.toDomainModel(): Pet {
    return Pet(
        id = petId,
        name = name,
        species = species,
        breed = breed,
        age = age,
        gender = gender,
        registerDate = registrationDate
    )
}

fun List<PetResponseDto>.toDomainModel(): List<Pet> {
    return this.map { dto ->
        Pet(
            id = dto.petId,
            name = dto.name,
            species = dto.species,
            breed = dto.breed,
            age = dto.age,
            gender = dto.gender,
            registerDate = dto.registrationDate
        )
    }
}

fun Pet.toPetRegisterRequestDto(): PetRegisterRequestDto {
    return PetRegisterRequestDto(
        name = name.take(255), // name은 최대 255자
        species = species.lowercase(), // "Dog" -> "dog"
        breed = breed.take(255), // breed는 최대 255자
        age = age.coerceAtLeast(0), // age는 0 이상
        gender = gender
    );
}

