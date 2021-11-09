package uz.pdp.dictionaryapp.retrofitmodel

data class Word(
	val phonetic: String? = null,
	val origin: String? = null,
	val phonetics: List<PhoneticsItem?>? = null,
	val word: String? = null,
	val meanings: List<MeaningsItem?>? = null
)

data class MeaningsItem(
	val partOfSpeech: String? = null,
	val definitions: List<DefinitionsItem?>? = null
)

data class PhoneticsItem(
	val text: String? = null,
	val audio: String? = null
)

data class DefinitionsItem(
	val synonyms: List<Any?>? = null,
	val antonyms: List<Any?>? = null,
	val definition: String? = null,
	val example: String? = null
)

