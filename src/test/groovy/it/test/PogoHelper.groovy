package it.test

class PogoHelper {
    def remover(List<String> exclude) {
        def origin = this.toMap()
        exclude.each {
            def levels = it.tokenize('.')
            if (levels.size() == 1) {
                origin.remove(levels[0])
            } else {
                def ref = origin
                levels[0..-2].each {
                    ref = ref[it]
                }
                if (ref instanceof Map)
                    ref.remove(levels[-1])
                else if (ref instanceof List) {
                    ref.each {
                        it.remove(levels[-1])
                    }
                } else {
                    ref = (ref as Object[]).minus(levels[-1])
                    origin.putAt(levels[0], ref)
                }
            }
        }
        def result = new Expando()
        origin.each {
            result.setProperty(it.key, it.value)
        }
        return result
    }

    def faker(List<String> exclude) {
        def origin = this.toMap()

        def getType = { value ->
            if (value.isNumber()) {
                return value.contains('.') ? value.toDouble() : value.toInteger()
            } else if (value.equalsIgnoreCase("null")) return null
            else if (value.equalsIgnoreCase("true")) return true
            else if (value.equalsIgnoreCase("false")) return false
            else return value.toString()
        }

        exclude.each {
            def param = it.tokenize('=')
            if (param.size() < 2) throw new Exception("There is no params in dataprovider. Please use PARAM = VALUE")
            def value =  getType(param[1])
            def levels = param[0].tokenize('.')
            if (levels.size() == 1) {
                origin[levels[0]] = value
            } else {
                def ref = origin
                levels[0..-2].each {
                    ref = ref[it]
                }
                if (ref instanceof Map)
                    ref[levels[-1]] = value
                else if (ref instanceof List) {
                    ref.each {
                        it[levels[-1]] = value
                    }
                }
            }
        }
        def result = new Expando()
        origin.each {
            result.setProperty(it.key, it.value)
        }
        return result
    }
}

trait Mappable {
    def toMap() {
        def map = [:]
        def fields = this.metaClass.getProperties().findAll { it.name != "class" }
        fields.each {
            def field = this.metaClass.getProperty(this, it.name)
            if (field instanceof Mappable)
                map[(it.name)] = field.toMap()
            else if (field instanceof Mappable[]) {
                def list = []
                field.each {
                    list << it.toMap()
                }

                map[it.name] = list
            } else
                map[(it.name)] = field
        }
        return map
    }
}