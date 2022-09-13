if not defined KH (
    set KH=c:\kh123
    echo "KH" is not set, it is set to "%KH%"
    echo %KH%
    echo "KH" is not set, it is set to "%KH%"
) else (
    echo KH is defined to "%KH%"
)
echo %KH%
echo "KH" is not set, it is set to "%KH%"
